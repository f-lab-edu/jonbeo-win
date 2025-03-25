package com.sdhong.jonbeowin.feature.jonbeocount.viewmodel

import com.sdhong.jonbeowin.base.BaseViewModel
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountItem
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.JonbeoCountUiState
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
class JonbeoCountViewModel @Inject constructor(
    private val jonbeoRepository: JonbeoRepository
) : BaseViewModel() {

    private val isEditMode = MutableStateFlow(false)

    private val checkedMap = MutableStateFlow<Map<Int, Boolean>>(emptyMap())

    val uiState: StateFlow<JonbeoCountUiState> = combine(
        jonbeoRepository.flowAllAssets(),
        isEditMode,
        checkedMap
    ) { assetList, isEditMode, checkedMap ->
        if (assetList.isNotEmpty()) {
            JonbeoCountUiState.Success(
                jonbeoCountItemList = assetList.map { asset ->
                    JonbeoCountItem(
                        asset = asset,
                        isEditMode = isEditMode,
                        isChecked = if (isEditMode) (checkedMap[asset.id] ?: false) else false
                    )
                },
                isEditMode = isEditMode
            )
        } else {
            JonbeoCountUiState.Empty
        }
    }.catch {
        emit(JonbeoCountUiState.Error)
    }.stateIn(
        initialValue = JonbeoCountUiState.Idle
    )

    private val _eventChannel = Channel<JonbeoCountEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()


    fun toggleEditMode() {
        if (uiState.value !is JonbeoCountUiState.Success) return

        launch {
            val preValue = isEditMode.value
            if (preValue) {
                val checkedAssetIdSet = checkedMap.value.keys
                jonbeoRepository.delete(checkedAssetIdSet)
                checkedMap.value = emptyMap()
            }
            isEditMode.value = !preValue
        }
    }

    fun onJonbeoCountItemClick(asset: Asset) {
        if (isEditMode.value) {
            checkedMap.value = checkedMap.value.toMutableMap().also { map ->
                val currentValue = map[asset.id] ?: false
                map[asset.id] = !currentValue
            }
        } else {
            launch {
                _eventChannel.send(JonbeoCountEvent.StartAsset(asset.id))
            }
        }
    }

    fun eventStartAddAsset() {
        launch {
            _eventChannel.send(JonbeoCountEvent.StartAsset())
        }
    }

    sealed interface JonbeoCountEvent {
        data class StartAsset(val assetId: Int? = null) : JonbeoCountEvent
    }
}