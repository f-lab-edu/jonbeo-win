package com.sdhong.jonbeowin.feature.asset.viewmodel

import android.icu.util.Calendar
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseViewModel
import com.sdhong.jonbeowin.feature.asset.AssetActivity
import com.sdhong.jonbeowin.feature.asset.uistate.AssetUiState
import com.sdhong.jonbeowin.local.model.Asset
import com.sdhong.jonbeowin.local.model.BuyDate
import com.sdhong.jonbeowin.repository.JonbeoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val jonbeoRepository: JonbeoRepository
) : BaseViewModel() {

    private val assetId = savedStateHandle.get<Int>(AssetActivity.EXTRA_ASSET_ID) ?: 0

    private val isAssetDetail = assetId != 0

    private val initialAsset = jonbeoRepository.flowAssetById(assetId)
        .stateIn(
            initialValue = Asset.Default
        )
    private val buyDate = MutableStateFlow(BuyDate.Default)

    val uiState: StateFlow<AssetUiState> = combine(
        initialAsset,
        buyDate
    ) { initialAsset, buyDate ->
        if (isAssetDetail) {
            if (buyDate == BuyDate.Default) {
                AssetUiState.AssetDetailInitial(initialAsset)
            } else {
                AssetUiState.AssetDetailDateSelected(buyDate)
            }
        } else {
            if (buyDate == BuyDate.Default) {
                AssetUiState.AddAssetInitial
            } else {
                AssetUiState.AddAssetDateSelected(buyDate)
            }
        }
    }.catch {
        emit(AssetUiState.Error)
    }.stateIn(
        initialValue = AssetUiState.Idle
    )

    private val _eventChannel = Channel<AssetEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()


    fun saveAsset(updatedName: String) {
        launch {
            if (validateAssetName(updatedName)) return@launch
            if (checkUserSetBuyDate()) return@launch

            val diffDays = getDiffDays()
            if (validateDiffDays(diffDays)) return@launch

            val updatedAsset = if (isAssetDetail) {
                initialAsset.value.copy(
                    name = updatedName,
                    dayCount = diffDays + 1,
                    buyDate = buyDate.value
                )
            } else {
                Asset(
                    name = updatedName,
                    dayCount = diffDays + 1,
                    buyDate = buyDate.value,
                    generatedTime = Calendar.getInstance().time.toString()
                )
            }

            jonbeoRepository.update(updatedAsset)

            eventFinishAsset()
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

    fun eventFinishAsset() {
        launch {
            _eventChannel.send(AssetEvent.FinishAsset)
        }
    }

    fun eventShowToast(@StringRes messageId: Int) {
        launch {
            _eventChannel.send(AssetEvent.ShowToast(messageId))
        }
    }

    sealed interface AssetEvent {
        data class ShowToast(@StringRes val messageId: Int) : AssetEvent
        data object FinishAsset : AssetEvent
    }
}