package com.sdhong.jonbeowin.feature.jonbeocount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseFragment
import com.sdhong.jonbeowin.databinding.FragmentJonbeoCountBinding
import com.sdhong.jonbeowin.feature.asset.AssetActivity
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.JonbeoCountUiState
import com.sdhong.jonbeowin.feature.jonbeocount.viewmodel.JonbeoCountViewModel
import com.sdhong.jonbeowin.feature.jonbeocount.viewmodel.JonbeoCountViewModel.JonbeoCountEvent
import com.sdhong.jonbeowin.util.collectFlow
import com.sdhong.jonbeowin.util.collectLatestFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JonbeoCountFragment : BaseFragment<FragmentJonbeoCountBinding>(
    bindingFactory = FragmentJonbeoCountBinding::inflate
) {
    private val viewModel: JonbeoCountViewModel by viewModels()
    private val jonbeoCountAdapter = JonbeoCountListAdapter(::onJonbeoCountItemClick)

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
            viewModel.eventStartAddAsset()
        }

        setCollectors()
    }

    private fun setCollectors() {
        viewLifecycleOwner.collectLatestFlow(viewModel.uiState) { uiState ->
            handleUiState(uiState)
        }

        viewLifecycleOwner.collectFlow(viewModel.eventFlow) { event ->
            handleEvent(event)
        }
    }

    private fun handleUiState(uiState: JonbeoCountUiState) {
        when (uiState) {
            is JonbeoCountUiState.Idle -> Unit

            is JonbeoCountUiState.Empty -> {
                jonbeoCountAdapter.submitList(emptyList())

                binding.textViewMessage.also {
                    it.visibility = View.VISIBLE
                    it.text = getString(R.string.jonbeo_asset_empty_message)
                    it.setTextColor(requireContext().getColor(R.color.dusk_gray))
                }
                binding.recyclerViewJonbeoCount.visibility = View.INVISIBLE
            }

            is JonbeoCountUiState.Success -> {
                jonbeoCountAdapter.submitList(uiState.jonbeoCountItemList)

                binding.toolbarJonbeocount.menu.findItem(R.id.menuEditAsset).title = getString(uiState.appBarButtonId)
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
            is JonbeoCountEvent.StartAsset -> {
                startActivity(AssetActivity.newIntent(requireContext(), event.assetId))
            }
        }
    }

    private fun onJonbeoCountItemClick(position: Int) {
        viewModel.onJonbeoCountItemClick(position)
    }
}