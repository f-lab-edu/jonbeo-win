package com.sdhong.jonbeowin.feature.encourage.view

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sdhong.jonbeowin.core.common.base.BaseDialogFragment
import com.sdhong.jonbeowin.core.common.extension.collectFlow
import com.sdhong.jonbeowin.feature.encourage.component.EncourageDialog
import com.sdhong.jonbeowin.feature.encourage.databinding.FragmentEncourageDialogBinding
import com.sdhong.jonbeowin.feature.encourage.viewmodel.EncourageDialogViewModel
import com.sdhong.jonbeowin.feature.encourage.viewmodel.EncourageDialogViewModel.EncourageDialogEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EncourageDialogFragment : BaseDialogFragment<FragmentEncourageDialogBinding>(
    bindingFactory = FragmentEncourageDialogBinding::inflate
) {

    private val viewModel: EncourageDialogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setCollectors()
    }

    private fun setUpView() {
        binding.composeView.setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            EncourageDialog(
                uiState = uiState,
                onCloseClick = viewModel::eventDialogClose,
                onSaveClick = viewModel::saveEncourage,
                onGenerateClick = viewModel::generateEncourage
            )
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is EncourageDialogEvent.Close -> {
                    dismiss()
                }
            }
        }
    }

}