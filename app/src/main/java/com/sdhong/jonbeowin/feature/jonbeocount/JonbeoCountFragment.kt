package com.sdhong.jonbeowin.feature.jonbeocount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseFragment
import com.sdhong.jonbeowin.databinding.FragmentJonbeoCountBinding
import com.sdhong.jonbeowin.local.model.Asset
import com.sdhong.jonbeowin.view.AddAssetActivity
import com.sdhong.jonbeowin.view.AssetDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JonbeoCountFragment : BaseFragment<FragmentJonbeoCountBinding>(
    bindingFactory = FragmentJonbeoCountBinding::inflate
) {
    private val viewModel: JonbeoCountViewModel by viewModels()
    private val jonbeoCountAdapter = JonbeoCountListAdapter(::onAssetItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarJonbeocount.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuEditAsset -> {
                    viewModel.toggleEditMode()
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
                viewModel.assetUiStateList.collectLatest {
                    jonbeoCountAdapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isEditMode.collectLatest { isEditMode ->
                    val title = getString(if (isEditMode) R.string.remove else R.string.edit)
                    binding.toolbarJonbeocount.menu.findItem(R.id.menuEditAsset).title = title
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    when (event) {
                        is JonbeoCountViewModel.JonbeoCountEvent.StartAssetDetail -> {
                            startActivity(
                                AssetDetailActivity.newIntent(
                                    requireContext(),
                                    event.assetId
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onAssetItemClick(asset: Asset) {
        viewModel.onAssetItemClick(asset)
    }
}