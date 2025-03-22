package com.sdhong.jonbeowin.feature.jonbeocount.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.AssetUiState
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.JonbeoCountUiState
import com.sdhong.jonbeowin.local.dao.AssetDao
import com.sdhong.jonbeowin.local.model.Asset
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
class JonbeoCountViewModel @Inject constructor(
    private val assetDao: AssetDao
) : ViewModel() {

    private val isEditMode = MutableStateFlow(false)

    private val checkedMap = MutableStateFlow<Map<Int, Boolean>>(emptyMap())

    val uiState: StateFlow<JonbeoCountUiState> = combine(
        assetDao.getAll(),
        isEditMode,
        checkedMap
    ) { assetList, isEditMode, checkedMap ->
        if (assetList.isNotEmpty()) {
            JonbeoCountUiState.Success(
                assetUiStateList = assetList.map { asset ->
                    AssetUiState(
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
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = JonbeoCountUiState.Idle
    )

    private val _eventChannel = Channel<JonbeoCountEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()


    fun toggleEditMode() {
        if (uiState.value !is JonbeoCountUiState.Success) return

        viewModelScope.launch {
            val preValue = isEditMode.value
            if (preValue) {
                val checkedAssetIdSet = checkedMap.value.keys
                assetDao.delete(checkedAssetIdSet)
                checkedMap.value = emptyMap()
            }
            isEditMode.value = !preValue
        }
    }

    fun onAssetItemClick(asset: Asset) {
        if (isEditMode.value) {
            checkedMap.value = checkedMap.value.toMutableMap().also { map ->
                val currentValue = map[asset.id] ?: false
                map[asset.id] = !currentValue
            }
        } else {
            viewModelScope.launch {
                _eventChannel.send(JonbeoCountEvent.StartAssetDetail(asset.id))
            }
        }
    }

    fun startAddAsset() {
        viewModelScope.launch {
            _eventChannel.send(JonbeoCountEvent.StartAddAsset)
        }
    }

    sealed interface JonbeoCountEvent {
        data object StartAddAsset : JonbeoCountEvent
        data class StartAssetDetail(val assetId: Int) : JonbeoCountEvent
    }
}