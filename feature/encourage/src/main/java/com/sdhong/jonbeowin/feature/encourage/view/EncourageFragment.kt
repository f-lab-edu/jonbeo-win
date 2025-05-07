package com.sdhong.jonbeowin.feature.encourage.view

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sdhong.jonbeowin.core.common.base.BaseFragment
import com.sdhong.jonbeowin.core.common.extension.collectFlow
import com.sdhong.jonbeowin.core.common.extension.collectLatestFlow
import com.sdhong.jonbeowin.feature.encourage.R
import com.sdhong.jonbeowin.feature.encourage.component.EncourageContent
import com.sdhong.jonbeowin.feature.encourage.databinding.FragmentEncourageBinding
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageUiState
import com.sdhong.jonbeowin.feature.encourage.viewmodel.EncourageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EncourageFragment : BaseFragment<FragmentEncourageBinding>(
    bindingFactory = FragmentEncourageBinding::inflate
) {
    private val viewModel: EncourageViewModel by viewModels()

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
        binding.composeView.setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            EncourageContent(
                uiState = uiState,
                onEncourageItemClick = viewModel::onEncourageItemClick
            )
        }

        binding.buttonGenerate.setOnClickListener {
            viewModel.eventShowEncourageDialog()
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.collectLatestFlow(viewModel.uiState) { uiState ->
            handleUiState(uiState)
        }

        viewLifecycleOwner.collectFlow(viewModel.eventFlow) { event ->
            handleEvent(event)
        }
    }

    private fun handleUiState(uiState: EncourageUiState) {
        when (uiState) {
            is EncourageUiState.Success -> {
                val title = if (uiState.isEditMode) R.string.remove else R.string.edit
                binding.toolbarEncourage.menu.findItem(R.id.menuEditAsset).title = getString(title)
            }

            else -> Unit
        }
    }

    private fun handleEvent(event: EncourageViewModel.EncourageEvent) {
        when (event) {
            is EncourageViewModel.EncourageEvent.ShowEncourageDialog -> {
                EncourageDialogFragment().show(childFragmentManager, null)
            }
        }
    }
}