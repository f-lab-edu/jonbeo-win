package com.sdhong.jonbeowin.feature.asset.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.sdhong.jonbeowin.core.common.base.BaseFragment
import com.sdhong.jonbeowin.core.common.extension.collectFlow
import com.sdhong.jonbeowin.feature.asset.R
import com.sdhong.jonbeowin.feature.asset.component.AssetContent
import com.sdhong.jonbeowin.feature.asset.databinding.FragmentAssetBinding
import com.sdhong.jonbeowin.feature.asset.viewmodel.AssetViewModel
import com.sdhong.jonbeowin.feature.asset.viewmodel.AssetViewModel.AssetEvent
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

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

        binding.toolbarAsset.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuClose -> {
                    viewModel.eventFinishAsset()
                    true
                }

                else -> false
            }
        }

        binding.composeView.setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            AssetContent(
                uiState = uiState,
                buttonTextId = if (viewModel.isAssetDetail) R.string.fix else R.string.save,
                onAssetNameChange = viewModel::setAssetName,
                onClickBuyDate = {
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
                },
                onClickConfirm = {
                    viewModel.saveAsset(it)
                }
            )
        }
    }

    private fun setCollectors() {
        collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is AssetEvent.ShowToast -> {
                    Toast.makeText(
                        requireContext(),
                        getString(event.assetToast.messageId),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AssetEvent.FinishAsset -> {
                    findNavController().popBackStack()
                }
            }
        }
    }
}
