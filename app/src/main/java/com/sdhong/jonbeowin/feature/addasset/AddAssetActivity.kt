package com.sdhong.jonbeowin.feature.addasset

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseActivity
import com.sdhong.jonbeowin.databinding.ActivityAddAssetBinding
import com.sdhong.jonbeowin.feature.addasset.uistate.AddAssetUiState
import com.sdhong.jonbeowin.feature.addasset.viewmodel.AddAssetViewModel
import com.sdhong.jonbeowin.feature.addasset.viewmodel.AddAssetViewModel.AddAssetEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class AddAssetActivity : BaseActivity<ActivityAddAssetBinding>(
    bindingFactory = ActivityAddAssetBinding::inflate
) {
    private val viewModel: AddAssetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpView()
        setCollectors()
    }

    private fun setUpView() {
        binding.toolbarAddAsset.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuClose -> {
                    viewModel.eventFinishAddAsset()
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

        binding.buttonSave.setOnClickListener {
            viewModel.saveAsset(binding.editTextAssetName.text.toString())
        }
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    when (uiState) {
                        is AddAssetUiState.Idle -> Unit

                        is AddAssetUiState.Success -> {
                            binding.textViewBuyDate.text = getString(
                                R.string.date_format,
                                uiState.buyDate.year,
                                uiState.buyDate.month,
                                uiState.buyDate.day
                            )
                        }

                        is AddAssetUiState.Error -> {
                            viewModel.eventShowToast(R.string.add_asset_error_message)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    when (event) {
                        is AddAssetEvent.ShowToast -> {
                            showToast(event.messageId)
                        }

                        is AddAssetEvent.FinishAddAsset -> {
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun showToast(@StringRes messageId: Int) {
        Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, AddAssetActivity::class.java)
        }
    }
}