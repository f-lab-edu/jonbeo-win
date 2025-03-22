package com.sdhong.jonbeowin.feature.addasset.viewmodel

import android.icu.util.Calendar
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.feature.addasset.uistate.AddAssetUiState
import com.sdhong.jonbeowin.feature.assetdetail.model.BuyDate
import com.sdhong.jonbeowin.local.dao.AssetDao
import com.sdhong.jonbeowin.local.model.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAssetViewModel @Inject constructor(
    private val assetDao: AssetDao
) : ViewModel() {

    private val buyDate = MutableStateFlow(BuyDate.Default)

    val uiState: StateFlow<AddAssetUiState> = buyDate.map { buyDate ->
        if (buyDate == BuyDate.Default) {
            AddAssetUiState.Idle
        } else {
            AddAssetUiState.Success(buyDate)
        }
    }.catch {
        emit(AddAssetUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddAssetUiState.Idle
    )

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
                    buyDateString = buyDate.value.formattedString,
                    generatedTime = Calendar.getInstance().time.toString()
                )
            )
            eventFinishAddAsset()
        }
    }

    private fun validateAssetName(assetName: String): Boolean {
        if (assetName.isBlank()) {
            eventShowToast(R.string.asset_name_empty_message)
            return true
        }
        return false
    }

    private fun checkUserSetBuyDate(): Boolean {
        if (buyDate.value == BuyDate.Default) {
            eventShowToast(R.string.date_empty_message)
            return true
        }
        return false
    }

    private fun getDiffDays(): Int {
        val buyDay = Calendar.getInstance().apply {
            set(buyDate.value.year, buyDate.value.month - 1, buyDate.value.day, 0, 0, 0)
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

    private fun validateDiffDays(diffDays: Int): Boolean {
        if (diffDays < 0) {
            eventShowToast(R.string.date_error_message)
            return true
        }
        return false
    }

    fun setBuyDate(year: Int, month: Int, day: Int) {
        buyDate.value = BuyDate(
            year = year,
            month = month,
            day = day
        )
    }

    fun eventFinishAddAsset() {
        viewModelScope.launch {
            _eventChannel.send(AddAssetEvent.FinishAddAsset)
        }
    }

    fun eventShowToast(@StringRes messageId: Int) {
        viewModelScope.launch {
            _eventChannel.send(AddAssetEvent.ShowToast(messageId))
        }
    }

    sealed interface AddAssetEvent {
        data class ShowToast(@StringRes val messageId: Int) : AddAssetEvent
        data object FinishAddAsset : AddAssetEvent
    }
}