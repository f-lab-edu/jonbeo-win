package com.sdhong.jonbeowin.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseFragment
import com.sdhong.jonbeowin.databinding.FragmentJonbeoCountBinding
import com.sdhong.jonbeowin.viewmodel.JonbeoCountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JonbeoCountFragment : BaseFragment<FragmentJonbeoCountBinding>(
    bindingFactory = FragmentJonbeoCountBinding::inflate
) {
    private val viewModel: JonbeoCountViewModel by viewModels()
    private val jonbeoCountAdapter = JonbeoCountListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarJonbeocount.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuEditAsset -> {
                    viewModel.editAssetList()
                    true
                }
                else -> false
            }
        }
        binding.recyclerViewJonbeoCount.adapter = jonbeoCountAdapter
        binding.buttonAdd.setOnClickListener {
            startActivity(AddAssetActivity.newIntent(requireContext()))
        }

        setCollectors()
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.assetList.collectLatest {
                    jonbeoCountAdapter.submitList(it)
                }
            }
        }
    }
}