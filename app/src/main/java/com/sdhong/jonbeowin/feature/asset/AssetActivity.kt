package com.sdhong.jonbeowin.feature.asset

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseActivity
import com.sdhong.jonbeowin.databinding.ActivityAssetBinding
import com.sdhong.jonbeowin.feature.asset.uistate.AssetUiState
import com.sdhong.jonbeowin.feature.asset.viewmodel.AssetViewModel
import com.sdhong.jonbeowin.feature.asset.viewmodel.AssetViewModel.AssetEvent
import com.sdhong.jonbeowin.util.collectFlow
import com.sdhong.jonbeowin.util.collectLatestFlow
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AssetActivity : BaseActivity<ActivityAssetBinding>(
    bindingFactory = ActivityAssetBinding::inflate
) {
    private val viewModel: AssetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpView()
        setCollectors()
    }

    private fun setUpView() {
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
                binding.toolbarAsset.title = getString(R.string.title_asset_detail)
                binding.buttonAssetConfirm.text = getString(R.string.fix)

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

            is AssetUiState.AddAssetInitial -> {
                binding.toolbarAsset.title = getString(R.string.title_add_asset)
                binding.buttonAssetConfirm.text = getString(R.string.save)
                binding.textViewBuyDate.text = getString(R.string.choose_date)
            }

            is AssetUiState.AssetDetailDateSelected -> {
                binding.toolbarAsset.title = getString(R.string.title_asset_detail)
                binding.buttonAssetConfirm.text = getString(R.string.fix)

                binding.textViewBuyDate.text = getString(
                    R.string.date_format,
                    uiState.buyDate.year,
                    uiState.buyDate.month,
                    uiState.buyDate.day
                )
            }

            is AssetUiState.AddAssetDateSelected -> {
                binding.toolbarAsset.title = getString(R.string.title_add_asset)
                binding.buttonAssetConfirm.text = getString(R.string.save)

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
                Toast.makeText(this, getString(event.messageId), Toast.LENGTH_SHORT).show()
            }

            is AssetEvent.FinishAsset -> {
                finish()
            }
        }
    }

    companion object {

        const val EXTRA_ASSET_ID = "EXTRA_ASSET_ID"

        fun newIntent(context: Context, assetId: Int? = null): Intent {
            return Intent(context, AssetActivity::class.java).apply {
                assetId?.let { putExtra(EXTRA_ASSET_ID, it) }
            }
        }
    }
}
