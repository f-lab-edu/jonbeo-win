package com.sdhong.jonbeowin.feature.encourage.view

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sdhong.jonbeowin.core.common.base.BaseFragment
import com.sdhong.jonbeowin.core.common.extension.collectFlow
import com.sdhong.jonbeowin.feature.encourage.component.EncourageContent
import com.sdhong.jonbeowin.feature.encourage.databinding.FragmentEncourageBinding
import com.sdhong.jonbeowin.feature.encourage.viewmodel.EncourageViewModel
import com.sdhong.jonbeowin.feature.encourage.viewmodel.EncourageViewModel.EncourageEvent
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
        binding.composeView.setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            EncourageContent(
                uiState = uiState,
                onEncourageItemClick = viewModel::onEncourageItemClick,
                onGenerateButtonClick = viewModel::eventShowEncourageDialog,
                toggleEditMode = viewModel::toggleEditMode
            )
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is EncourageEvent.ShowEncourageDialog -> {
                    EncourageDialogFragment().show(childFragmentManager, null)
                }
            }
        }
    }

}