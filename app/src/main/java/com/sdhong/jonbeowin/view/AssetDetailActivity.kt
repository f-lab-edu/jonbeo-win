package com.sdhong.jonbeowin.view

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
import com.sdhong.jonbeowin.databinding.ActivityAssetDetailBinding
import com.sdhong.jonbeowin.local.model.BuyDate
import com.sdhong.jonbeowin.viewmodel.AssetDetailViewModel
import com.sdhong.jonbeowin.viewmodel.AssetDetailViewModel.AssetDetailEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
                    viewModel.finishAssetDetail()
                    true
                }

                else -> false
            }
        }

        binding.textViewCalendar.setOnClickListener {
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.buyDate.collectLatest { buyDate ->
                    if (buyDate == BuyDate.Default) {
                        binding.textViewCalendar.text = getString(R.string.choose_date)
                    } else {
                        binding.textViewCalendar.text = getString(
                            R.string.date_format,
                            buyDate.year,
                            buyDate.month,
                            buyDate.day
                        )
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    when (event) {
                        is AssetDetailEvent.ShowToast -> {
                            showToast(event.messageId)
                        }

                        is AssetDetailEvent.FinishAssetDetail -> {
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
            return Intent(context, AssetDetailActivity::class.java)
        }
    }
}