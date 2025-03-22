package com.sdhong.jonbeowin.feature.assetdetail.viewmodel

import android.icu.util.Calendar
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.feature.assetdetail.AssetDetailActivity
import com.sdhong.jonbeowin.feature.assetdetail.uistate.AssetDetailUiState
import com.sdhong.jonbeowin.local.dao.AssetDao
import com.sdhong.jonbeowin.local.model.Asset
import com.sdhong.jonbeowin.local.model.BuyDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val assetDao: AssetDao
) : ViewModel() {

    private val assetId = savedStateHandle.get<Int>(AssetDetailActivity.ASSET_ID) ?: 0

    private val initialAsset = assetDao.getAssetById(assetId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Asset.Default
    )
    private val buyDate = MutableStateFlow(BuyDate.Default)

    val uiState: StateFlow<AssetDetailUiState> = combine(
        initialAsset,
        buyDate
    ) { initialAsset, buyDate ->
        if (buyDate == BuyDate.Default) {
            AssetDetailUiState.Initial(initialAsset)
        } else {
            AssetDetailUiState.Success(buyDate)
        }
    }.catch {
        emit(AssetDetailUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AssetDetailUiState.Idle
    )

    private val _eventChannel = Channel<AssetDetailEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()


    fun fixAsset(updatedName: String) {
        viewModelScope.launch {
            if (validateAssetName(updatedName)) return@launch
            if (checkUserSetBuyDate()) return@launch

            val diffDays = getDiffDays()
            if (validateDiffDays(diffDays)) return@launch

            assetDao.update(
                initialAsset.value.copy(
                    name = updatedName,
                    dayCount = diffDays + 1,
                    buyDate = buyDate.value
                )
            )
            eventFinishAssetDetail()
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

    fun eventFinishAssetDetail() {
        viewModelScope.launch {
            _eventChannel.send(AssetDetailEvent.FinishAssetDetail)
        }
    }

    fun eventShowToast(@StringRes messageId: Int) {
        viewModelScope.launch {
            _eventChannel.send(AssetDetailEvent.ShowToast(messageId))
        }
    }

    sealed interface AssetDetailEvent {
        data class ShowToast(@StringRes val messageId: Int) : AssetDetailEvent
        data object FinishAssetDetail : AssetDetailEvent
    }
}