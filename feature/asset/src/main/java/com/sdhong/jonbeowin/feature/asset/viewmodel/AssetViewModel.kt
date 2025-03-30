package com.sdhong.jonbeowin.feature.asset.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.sdhong.jonbeowin.core.common.base.BaseViewModel
import com.sdhong.jonbeowin.core.domain.usecase.GetAssetUseCase
import com.sdhong.jonbeowin.core.domain.usecase.UpdateAssetUseCase
import com.sdhong.jonbeowin.feature.asset.enum.AssetToast
import com.sdhong.jonbeowin.feature.asset.mapper.toDomain
import com.sdhong.jonbeowin.feature.asset.mapper.toPresentation
import com.sdhong.jonbeowin.feature.asset.model.AssetModel
import com.sdhong.jonbeowin.feature.asset.model.BuyDateModel
import com.sdhong.jonbeowin.feature.asset.uistate.AssetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AssetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAssetUseCase: GetAssetUseCase,
    private val updateAssetUseCase: UpdateAssetUseCase
) : BaseViewModel() {

    private val assetId = (savedStateHandle["assetId"] ?: "").let {
        if (it.isBlank()) {
            0
        } else {
            it.toInt()
        }
    }

    val isAssetDetail = assetId != 0

    private val initialAsset = getAssetUseCase(assetId)
        .map { it.toPresentation() }
        .catch {
            emit(AssetModel.Default)
        }
        .stateIn(
            initialValue = AssetModel.Default
        )
    private val buyDate = MutableStateFlow(BuyDateModel.Default)

    val uiState: StateFlow<AssetUiState> = combine(
        initialAsset,
        buyDate
    ) { initialAsset, buyDate ->
        if (buyDate == BuyDateModel.Default) {
            if (isAssetDetail) {
                setBuyDate(initialAsset.buyDate.year, initialAsset.buyDate.month, initialAsset.buyDate.day)
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
                AssetModel(
                    id = 0,
                    name = updatedName,
                    dayCount = diffDays + 1,
                    buyDate = buyDate.value,
                    createdAt = Calendar.getInstance().time.toString()
                )
            }

            updateAssetUseCase(updatedAsset.toDomain())

            eventFinishAsset()
        }
    }

    private suspend fun validateAssetName(assetName: String): Boolean {
        if (assetName.isBlank()) {
            _eventChannel.send(AssetEvent.ShowToast(AssetToast.ASSET_NAME_EMPTY))
            return true
        }
        return false
    }

    private suspend fun checkUserSetBuyDate(): Boolean {
        if (buyDate.value == BuyDateModel.Default) {
            _eventChannel.send(AssetEvent.ShowToast(AssetToast.ASSET_BUY_DATE_EMPTY))
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
            _eventChannel.send(AssetEvent.ShowToast(AssetToast.ASSET_BUY_DATE_INVALID))
            return true
        }
        return false
    }

    fun setBuyDate(year: Int, month: Int, day: Int) {
        buyDate.value = BuyDateModel(
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
        data class ShowToast(val assetToast: AssetToast) : AssetEvent
        data object FinishAsset : AssetEvent
    }
}