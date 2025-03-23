package com.sdhong.jonbeowin.feature.assetdetail

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseActivity
import com.sdhong.jonbeowin.databinding.ActivityAssetDetailBinding
import com.sdhong.jonbeowin.feature.assetdetail.uistate.AssetDetailUiState
import com.sdhong.jonbeowin.feature.assetdetail.viewmodel.AssetDetailViewModel
import com.sdhong.jonbeowin.feature.assetdetail.viewmodel.AssetDetailViewModel.AssetDetailEvent
import com.sdhong.jonbeowin.util.collectFlow
import com.sdhong.jonbeowin.util.collectLatestFlow
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AssetDetailActivity : BaseActivity<ActivityAssetDetailBinding>(
    bindingFactory = ActivityAssetDetailBinding::inflate
) {
    private val viewModel: AssetDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpView()
        setCollectors()
    }

    private fun setUpView() {
        binding.toolbarAssetDetail.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuClose -> {
                    viewModel.eventFinishAssetDetail()
                    true
                }

                else -> false
            }
        }

        binding.textViewBuyDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, day ->
                    viewModel.setBuyDate(year, month + 1, day)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        binding.buttonFix.setOnClickListener {
            viewModel.fixAsset(binding.editTextAssetName.text.toString())
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

    private fun handleUiState(uiState: AssetDetailUiState) {
        when (uiState) {
            is AssetDetailUiState.Idle -> Unit

            is AssetDetailUiState.Initial -> {
                val asset = uiState.initialAsset
                val buyDate = asset.buyDate
                binding.editTextAssetName.setText(asset.name)
                binding.textViewBuyDate.text = getString(
                    R.string.date_format,
                    buyDate.year,
                    buyDate.month,
                    buyDate.day
                )

                viewModel.setBuyDate(buyDate.year, buyDate.month, buyDate.day)
            }

            is AssetDetailUiState.Success -> {
                val buyDate = uiState.buyDate
                binding.textViewBuyDate.text = getString(
                    R.string.date_format,
                    buyDate.year,
                    buyDate.month,
                    buyDate.day
                )
            }

            is AssetDetailUiState.Error -> {
                viewModel.eventShowToast(R.string.asset_detail_error_message)
            }
        }
    }

    private fun handleEvent(event: AssetDetailEvent) {
        when (event) {
            is AssetDetailEvent.ShowToast -> {
                Toast.makeText(this, getString(event.messageId), Toast.LENGTH_SHORT).show()
            }

            is AssetDetailEvent.FinishAssetDetail -> {
                finish()
            }
        }
    }

    companion object {

        const val ASSET_ID = "ASSET_ID"

        fun newIntent(context: Context, assetId: Int): Intent {
            return Intent(context, AssetDetailActivity::class.java).putExtra(ASSET_ID, assetId)
        }
    }
}
