package com.sdhong.jonbeowin.feature.asset.viewmodel

import android.icu.util.Calendar
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseViewModel
import com.sdhong.jonbeowin.feature.asset.AssetActivity
import com.sdhong.jonbeowin.feature.asset.model.BuyDate
import com.sdhong.jonbeowin.feature.asset.uistate.AssetBasicUiState
import com.sdhong.jonbeowin.feature.asset.uistate.AssetUiState
import com.sdhong.jonbeowin.local.model.Asset
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

    val basicUiState = AssetBasicUiState(
        appBarTitleId = if (isAssetDetail) R.string.title_asset_detail else R.string.title_add_asset,
        buttonTextId = if (isAssetDetail) R.string.fix else R.string.save
    )

    private val initialAsset = jonbeoRepository.flowAssetById(assetId)
        .stateIn(
            initialValue = Asset.Default
        )
    private val buyDate = MutableStateFlow(BuyDate.Default)

    val uiState: StateFlow<AssetUiState> = combine(
        initialAsset,
        buyDate
    ) { initialAsset, buyDate ->
        if (buyDate == BuyDate.Default) {
            if (isAssetDetail) {
                AssetUiState.AssetDetailInitial(initialAsset)
            } else {
                AssetUiState.AddAssetInitial
            }
        } else {
            AssetUiState.AssetDateSelected(buyDate)
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
                    createdAt = Calendar.getInstance().time.toString()
                )
            }

            jonbeoRepository.update(updatedAsset)

            eventFinishAsset()
        }
    }

    private suspend fun validateAssetName(assetName: String): Boolean {
        if (assetName.isBlank()) {
            _eventChannel.send(AssetEvent.ShowToast(R.string.asset_name_empty_message))
            return true
        }
        return false
    }

    private suspend fun checkUserSetBuyDate(): Boolean {
        if (buyDate.value == BuyDate.Default) {
            _eventChannel.send(AssetEvent.ShowToast(R.string.date_empty_message))
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

    private suspend fun validateDiffDays(diffDays: Int): Boolean {
        if (diffDays < 0) {
            _eventChannel.send(AssetEvent.ShowToast(R.string.date_error_message))
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

    sealed interface AssetEvent {
        data class ShowToast(@StringRes val messageId: Int) : AssetEvent
        data object FinishAsset : AssetEvent
    }
}