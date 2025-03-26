package com.sdhong.jonbeowin.feature.encourage

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseDialogFragment
import com.sdhong.jonbeowin.databinding.FragmentEncourageDialogBinding
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageDialogUiState
import com.sdhong.jonbeowin.feature.encourage.viewmodel.EncourageDialogViewModel
import com.sdhong.jonbeowin.util.collectFlow
import com.sdhong.jonbeowin.util.collectLatestFlow
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
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        binding.buttonEncourageDialogClose.setOnClickListener {
            viewModel.eventDialogClose()
        }

        binding.buttonEncourageDialogSave.setOnClickListener {
            viewModel.saveEncourage()
        }

        binding.buttonEncourageDialogGenerate.setOnClickListener {
            viewModel.generateEncourage()
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

    private fun handleUiState(uiState: EncourageDialogUiState) {
        when (uiState) {
            EncourageDialogUiState.Loading -> {
                binding.run {
                    buttonEncourageDialogSave.isEnabled = false
                    buttonEncourageDialogGenerate.isEnabled = false

                    progressBarEncourageDialog.visibility = View.VISIBLE
                    textViewEncourageDialog.visibility = View.INVISIBLE
                }
            }

            is EncourageDialogUiState.Success -> {
                binding.run {
                    buttonEncourageDialogSave.isEnabled = true
                    buttonEncourageDialogGenerate.isEnabled = true

                    progressBarEncourageDialog.visibility = View.GONE
                    textViewEncourageDialog.visibility = View.VISIBLE

                    textViewEncourageDialog.text = uiState.content
                }
            }

            EncourageDialogUiState.Error -> {
                binding.run {
                    buttonEncourageDialogSave.isEnabled = true
                    buttonEncourageDialogGenerate.isEnabled = true

                    progressBarEncourageDialog.visibility = View.GONE
                    textViewEncourageDialog.visibility = View.VISIBLE

                    textViewEncourageDialog.text = getString(R.string.encourage_dialog_error_message)
                }
            }
        }
    }

    private fun handleEvent(event: EncourageDialogViewModel.EncourageDialogEvent) {
        when (event) {
            is EncourageDialogViewModel.EncourageDialogEvent.Close -> {
                dismiss()
            }
        }
    }

}