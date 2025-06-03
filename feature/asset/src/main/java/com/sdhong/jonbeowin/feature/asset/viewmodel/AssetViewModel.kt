package com.sdhong.jonbeowin.feature.asset.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AssetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getAssetUseCase: GetAssetUseCase,
    private val updateAssetUseCase: UpdateAssetUseCase
) : ViewModel() {

    private val assetId = savedStateHandle.get<Int>(EXTRA_ASSET_ID) ?: 0

    val isAssetDetail = assetId != 0

    private val asset: MutableStateFlow<AssetModel> = MutableStateFlow(AssetModel.Default)

    private val _uiState: MutableStateFlow<AssetUiState> = MutableStateFlow(AssetUiState.Idle)
    val uiState: StateFlow<AssetUiState> = asset.map<AssetModel, AssetUiState> {
        AssetUiState.Success(it)
    }.catch {
        emit(AssetUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AssetUiState.Idle
    )

    init {
        if (isAssetDetail) {
            getAssetUseCase(assetId)
                .onEach {
                    asset.value = it.toPresentation()
                }
                .catch {
                    _uiState.value = AssetUiState.Error
                }
                .launchIn(viewModelScope)
        }
    }

    private val _eventChannel = Channel<AssetEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()


    fun saveAsset(updatedName: String) {
        viewModelScope.launch {
            if (validateAssetName(updatedName)) return@launch
            if (checkUserSetBuyDate()) return@launch

            val diffDays = getDiffDays()
            if (validateDiffDays(diffDays)) return@launch

            val updatedAsset = if (isAssetDetail) {
                asset.value.copy(
                    name = updatedName,
                    dayCount = diffDays + 1,
                    buyDate = asset.value.buyDate
                )
            } else {
                AssetModel(
                    id = 0,
                    name = updatedName,
                    dayCount = diffDays + 1,
                    buyDate = asset.value.buyDate,
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
        if (asset.value.buyDate == BuyDateModel.Default) {
            _eventChannel.send(AssetEvent.ShowToast(AssetToast.ASSET_BUY_DATE_EMPTY))
            return true
        }
        return false
    }

    private fun getDiffDays(): Int {
        val buyDay = Calendar.getInstance().apply {
            set(asset.value.buyDate.year, asset.value.buyDate.month - 1, asset.value.buyDate.day, 0, 0, 0)
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

    fun setAssetName(name: String) {
        asset.value = asset.value.copy(name = name)
    }

    fun setBuyDate(year: Int, month: Int, day: Int) {
        asset.value = asset.value.copy(
            buyDate = BuyDateModel(
                year = year,
                month = month,
                day = day
            )
        )
    }

    fun eventFinishAsset() {
        viewModelScope.launch {
            _eventChannel.send(AssetEvent.FinishAsset)
        }
    }

    sealed interface AssetEvent {
        data class ShowToast(val assetToast: AssetToast) : AssetEvent
        data object FinishAsset : AssetEvent
    }

    companion object {
        private const val EXTRA_ASSET_ID = "assetId"
    }
}