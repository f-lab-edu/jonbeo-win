package com.sdhong.jonbeowin.feature.jonbeocount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdhong.jonbeowin.local.dao.AssetDao
import com.sdhong.jonbeowin.local.model.Asset
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.AssetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JonbeoCountViewModel @Inject constructor(
    private val assetDao: AssetDao
) : ViewModel() {

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode = _isEditMode.asStateFlow()

    private val checkedMap = MutableStateFlow<Map<Int, Boolean>>(emptyMap())

    val assetUiStateList: StateFlow<List<AssetUiState>> = combine(
        assetDao.getAll(),
        _isEditMode,
        checkedMap
    ) { assetList, isEditMode, checkedMap ->
        assetList.map { asset ->
            AssetUiState(
                asset = asset,
                isEditMode = isEditMode,
                isChecked = if (isEditMode) (checkedMap[asset.id] ?: false) else false
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _eventChannel = Channel<JonbeoCountEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    fun toggleEditMode() {
        viewModelScope.launch {
            val preValue = _isEditMode.value
            if (preValue) {
                val checkedAssetIdSet = checkedMap.value.keys
                assetDao.delete(checkedAssetIdSet)
                checkedMap.value = emptyMap()
            }
            _isEditMode.value = !preValue
        }
    }

    fun onAssetItemClick(asset: Asset) {
        if (_isEditMode.value) {
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

    sealed interface JonbeoCountEvent {
        data class StartAssetDetail(val assetId: Int) : JonbeoCountEvent
    }
}