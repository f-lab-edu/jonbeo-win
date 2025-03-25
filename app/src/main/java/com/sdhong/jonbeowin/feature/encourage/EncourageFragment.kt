package com.sdhong.jonbeowin.feature.encourage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseFragment
import com.sdhong.jonbeowin.databinding.FragmentEncourageBinding
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageUiState
import com.sdhong.jonbeowin.feature.encourage.viewmodel.EncourageViewModel
import com.sdhong.jonbeowin.local.model.Encourage
import com.sdhong.jonbeowin.util.collectFlow
import com.sdhong.jonbeowin.util.collectLatestFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EncourageFragment : BaseFragment<FragmentEncourageBinding>(
    bindingFactory = FragmentEncourageBinding::inflate
) {
    private val viewModel: EncourageViewModel by viewModels()
    private val encourageAdapter = EncourageListAdapter(::onEncourageItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setCollectors()
    }

    private fun setUpView() {
        binding.toolbarEncourage.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuEditAsset -> {
                    viewModel.toggleEditMode()
                    true
                }

                else -> false
            }
        }
        binding.recyclerViewEncourage.adapter = encourageAdapter
        binding.buttonGenerate.setOnClickListener {
            viewModel.eventShowEncourageDialog()
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.collectLatestFlow(viewModel.uiState) { uiState ->
            when (uiState) {
                EncourageUiState.Idle -> Unit

                EncourageUiState.Empty -> {
                    encourageAdapter.submitList(emptyList())

                    binding.textViewMessage.also {
                        it.visibility = View.VISIBLE
                        it.text = getString(R.string.encourage_list_empty_message)
                        it.setTextColor(requireContext().getColor(R.color.dusk_gray))
                    }
                    binding.recyclerViewEncourage.visibility = View.INVISIBLE
                }

                is EncourageUiState.Success -> {
                    encourageAdapter.submitList(uiState.encourageItemList)

                    binding.toolbarEncourage.menu.findItem(R.id.menuEditAsset).title = getString(uiState.appBarButtonId)
                    binding.recyclerViewEncourage.visibility = View.VISIBLE
                    binding.textViewMessage.visibility = View.GONE
                }

                EncourageUiState.Error -> {
                    binding.textViewMessage.also {
                        it.visibility = View.VISIBLE
                        it.text = getString(R.string.encourage_list_error_message)
                        it.setTextColor(requireContext().getColor(R.color.red))
                    }
                    binding.recyclerViewEncourage.visibility = View.INVISIBLE
                }
            }
        }

        viewLifecycleOwner.collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is EncourageViewModel.EncourageEvent.ShowEncourageDialog -> {
                    EncourageDialogFragment().show(childFragmentManager, null)
                }
            }
        }
    }

    private fun onEncourageItemClick(encourage: Encourage) {
        viewModel.onEncourageItemClick(encourage)
    }
}