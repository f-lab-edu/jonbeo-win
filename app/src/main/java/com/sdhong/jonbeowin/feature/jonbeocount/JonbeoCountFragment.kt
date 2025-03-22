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
import com.sdhong.jonbeowin.feature.assetdetail.AssetDetailActivity
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.JonbeoCountUiState
import com.sdhong.jonbeowin.feature.jonbeocount.viewmodel.JonbeoCountViewModel
import com.sdhong.jonbeowin.feature.jonbeocount.viewmodel.JonbeoCountViewModel.JonbeoCountEvent
import com.sdhong.jonbeowin.local.model.Asset
import com.sdhong.jonbeowin.view.AddAssetActivity
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
            viewModel.startAddAsset()
        }

        setCollectors()
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    handleUiState(uiState)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleUiState(uiState: JonbeoCountUiState) {
        when (uiState) {
            is JonbeoCountUiState.Idle -> Unit

            is JonbeoCountUiState.Empty -> {
                binding.textViewMessage.also {
                    it.visibility = View.VISIBLE
                    it.text = getString(R.string.jonbeo_asset_empty_message)
                    it.setTextColor(requireContext().getColor(R.color.dusk_gray))
                }
                binding.recyclerViewJonbeoCount.visibility = View.INVISIBLE
            }

            is JonbeoCountUiState.Success -> {
                jonbeoCountAdapter.submitList(uiState.assetUiStateList)

                val title = getString(if (uiState.isEditMode) R.string.remove else R.string.edit)
                binding.toolbarJonbeocount.menu.findItem(R.id.menuEditAsset).title = title

                binding.recyclerViewJonbeoCount.visibility = View.VISIBLE
                binding.textViewMessage.visibility = View.GONE
            }

            is JonbeoCountUiState.Error -> {
                binding.textViewMessage.also {
                    it.visibility = View.VISIBLE
                    it.text = getString(R.string.jonbeo_asset_error_message)
                    it.setTextColor(requireContext().getColor(R.color.red))
                }
                binding.recyclerViewJonbeoCount.visibility = View.INVISIBLE
            }
        }
    }

    private fun handleEvent(event: JonbeoCountEvent) {
        when (event) {
            is JonbeoCountEvent.StartAddAsset -> {
                startActivity(AddAssetActivity.newIntent(requireContext()))
            }

            is JonbeoCountEvent.StartAssetDetail -> {
                startActivity(AssetDetailActivity.newIntent(requireContext(), event.assetId))
            }
        }
    }

    private fun onAssetItemClick(asset: Asset) {
        viewModel.onAssetItemClick(asset)
    }
}