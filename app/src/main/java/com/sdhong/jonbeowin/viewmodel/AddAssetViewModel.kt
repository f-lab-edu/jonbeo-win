package com.sdhong.jonbeowin.viewmodel

import android.icu.util.Calendar
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.feature.assetdetail.model.BuyDate
import com.sdhong.jonbeowin.local.dao.AssetDao
import com.sdhong.jonbeowin.local.model.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAssetViewModel @Inject constructor(
    private val assetDao: AssetDao
) : ViewModel() {

    private val _buyDate = MutableStateFlow(BuyDate.Default)
    val buyDate = _buyDate.asStateFlow()

    private val _eventChannel = Channel<AddAssetEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    fun saveAsset(assetName: String) {
        viewModelScope.launch {
            if (validateAssetName(assetName)) return@launch
            if (checkUserSetBuyDate()) return@launch

            val diffDays = getDiffDays()
            if (validateDiffDays(diffDays)) return@launch

            assetDao.update(
                Asset(
                    name = assetName,
                    dayCount = diffDays + 1,
                    buyDateString = _buyDate.value.formattedString,
                    generatedTime = Calendar.getInstance().time.toString()
                )
            )
            finishAddAsset()
        }
    }

    private suspend fun validateAssetName(assetName: String): Boolean {
        if (assetName.isBlank()) {
            _eventChannel.send(AddAssetEvent.ShowToast(R.string.asset_name_empty_message))
            return true
        }
        return false
    }

    private suspend fun checkUserSetBuyDate(): Boolean {
        if (_buyDate.value == BuyDate.Default) {
            _eventChannel.send(AddAssetEvent.ShowToast(R.string.date_empty_message))
            return true
        }
        return false
    }

    private fun getDiffDays(): Int {
        val buyDay = Calendar.getInstance().apply {
            set(_buyDate.value.year, _buyDate.value.month - 1, _buyDate.value.day, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val diffDays = ((today - buyDay) / (1000 * 60 * 60 * 24)).toInt()
        return diffDays
    }

    private suspend fun validateDiffDays(diffDays: Int): Boolean {
        if (diffDays < 0) {
            _eventChannel.send(AddAssetEvent.ShowToast(R.string.date_error_message))
            return true
        }
        return false
    }

    fun setBuyDate(year: Int, month: Int, day: Int) {
        _buyDate.value = BuyDate(
            year = year,
            month = month,
            day = day
        )
    }

    fun finishAddAsset() {
        viewModelScope.launch {
            _eventChannel.send(AddAssetEvent.FinishAddAsset)
        }
    }

    sealed interface AddAssetEvent {
        data class ShowToast(@StringRes val messageId: Int) : AddAssetEvent
        data object FinishAddAsset : AddAssetEvent
    }
}