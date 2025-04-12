package com.sdhong.jonbeowin.feature.asset.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sdhong.jonbeowin.core.common.base.BaseFragment
import com.sdhong.jonbeowin.core.common.extension.collectFlow
import com.sdhong.jonbeowin.core.common.extension.collectLatestFlow
import com.sdhong.jonbeowin.core.common.navigation.MainNavigator
import com.sdhong.jonbeowin.feature.asset.R
import com.sdhong.jonbeowin.feature.asset.databinding.FragmentAssetBinding
import com.sdhong.jonbeowin.feature.asset.uistate.AssetUiState
import com.sdhong.jonbeowin.feature.asset.viewmodel.AssetViewModel
import com.sdhong.jonbeowin.feature.asset.viewmodel.AssetViewModel.AssetEvent
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class AssetFragment : BaseFragment<FragmentAssetBinding>(
    bindingFactory = FragmentAssetBinding::inflate
) {

    private val viewModel: AssetViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setCollectors()
    }

    private fun setUpView() {
        binding.toolbarAsset.title = getString(
            if (viewModel.isAssetDetail) R.string.title_asset_detail
            else R.string.title_add_asset
        )
        binding.buttonAssetConfirm.text = getString(
            if (viewModel.isAssetDetail) R.string.fix
            else R.string.save
        )

        binding.toolbarAsset.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuClose -> {
                    viewModel.eventFinishAsset()
                    true
                }

                else -> false
            }
        }

        binding.textViewBuyDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    viewModel.setBuyDate(year, month + 1, day)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        binding.buttonAssetConfirm.setOnClickListener {
            viewModel.saveAsset(binding.editTextAssetName.text.toString())
        }
    }

    private fun setCollectors() {
        collectLatestFlow(viewModel.uiState) { uiState ->
            handleUiState(uiState)
        }

        collectFlow(viewModel.eventFlow) { event ->
            handleEvent(event)
        }
    }

    private fun handleUiState(uiState: AssetUiState) {
        when (uiState) {
            is AssetUiState.Idle -> Unit

            is AssetUiState.AssetDetailInitial -> {
                val asset = uiState.initialAsset
                val buyDate = asset.buyDate
                binding.editTextAssetName.setText(asset.name)
                binding.textViewBuyDate.text = getString(
                    R.string.date_format,
                    buyDate.year,
                    buyDate.month,
                    buyDate.day
                )
            }

            is AssetUiState.AddAssetInitial -> {
                binding.textViewBuyDate.text = getString(R.string.choose_date)
            }

            is AssetUiState.AssetDateSelected -> {
                binding.textViewBuyDate.text = getString(
                    R.string.date_format,
                    uiState.buyDate.year,
                    uiState.buyDate.month,
                    uiState.buyDate.day
                )
            }

            is AssetUiState.Error -> {
                binding.textViewAssetError.visibility = View.VISIBLE
                binding.cardViewAsset.visibility = View.GONE
                binding.space.visibility = View.GONE
                binding.buttonAssetConfirm.visibility = View.GONE
            }
        }
    }

    private fun handleEvent(event: AssetEvent) {
        when (event) {
            is AssetEvent.ShowToast -> {
                Toast.makeText(requireContext(), getString(event.assetToast.messageId), Toast.LENGTH_SHORT).show()
            }

            is AssetEvent.FinishAsset -> {
                findNavController().popBackStack()
            }
        }
    }
}
