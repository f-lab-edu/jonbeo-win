package com.sdhong.jonbeowin.feature.jonbeocount.viewmodel

import com.sdhong.jonbeowin.R
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
    private val checkedIdSet = MutableStateFlow<Set<Int>>(emptySet())

    val uiState: StateFlow<JonbeoCountUiState> = combine(
        jonbeoRepository.flowAllAssets(),
        isEditMode,
        checkedIdSet
    ) { assetList, isEditMode, checkedIdSet ->
        setOf(1, 2, 3)
        if (assetList.isNotEmpty()) {
            JonbeoCountUiState.Success(
                jonbeoCountItemList = assetList.map { asset ->
                    JonbeoCountItem(
                        asset = asset,
                        isEditMode = isEditMode,
                        isChecked = if (isEditMode) checkedIdSet.contains(asset.id) else false
                    )
                },
                appBarButtonId = if (isEditMode) R.string.remove else R.string.edit
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
                jonbeoRepository.delete(checkedIdSet.value)
                checkedIdSet.value = emptySet()
            }
            isEditMode.value = !preValue
        }
    }

    fun onJonbeoCountItemClick(asset: Asset) {
        if (isEditMode.value) {
            checkedIdSet.value = checkedIdSet.value.toMutableSet().also { set ->
                if (set.contains(asset.id)) {
                    set.remove(asset.id)
                } else {
                    set.add(asset.id)
                }
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