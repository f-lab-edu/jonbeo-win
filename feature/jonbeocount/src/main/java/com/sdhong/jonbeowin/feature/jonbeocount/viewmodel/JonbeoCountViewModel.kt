package com.sdhong.jonbeowin.feature.jonbeocount.viewmodel

import com.sdhong.jonbeowin.core.common.base.BaseViewModel
import com.sdhong.jonbeowin.core.domain.usecase.DeleteAssetUseCase
import com.sdhong.jonbeowin.core.domain.usecase.GetAssetListUseCase
import com.sdhong.jonbeowin.jonbeocount.R
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountModel
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.JonbeoCountUiState
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
    getAssetListUseCase: GetAssetListUseCase,
    private val deleteAssetUseCase: DeleteAssetUseCase
) : BaseViewModel() {

    private val isEditMode = MutableStateFlow(false)
    private val checkedIdSet = MutableStateFlow<Set<Int>>(emptySet())

    val uiState: StateFlow<JonbeoCountUiState> = combine(
        getAssetListUseCase(),
        isEditMode,
        checkedIdSet
    ) { assetList, isEditMode, checkedIdSet ->
        if (assetList.isNotEmpty()) {
            JonbeoCountUiState.Success(
                jonbeoCountItemList = assetList.map { asset ->
                    JonbeoCountModel(
                        id = asset.id,
                        name = asset.name,
                        dayCount = asset.dayCount,
                        buyDate = asset.buyDate,
                        createdAt = asset.createdAt,
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
                deleteAssetUseCase(checkedIdSet.value)
                checkedIdSet.value = emptySet()
            }
            isEditMode.value = !preValue
        }
    }

    fun onJonbeoCountItemClick(position: Int) {
        val assetId = (uiState.value as? JonbeoCountUiState.Success ?: return)
            .jonbeoCountItemList[position]
            .id

        if (isEditMode.value) {
            checkedIdSet.value = checkedIdSet.value.toMutableSet().also { set ->
                if (set.contains(assetId)) {
                    set.remove(assetId)
                } else {
                    set.add(assetId)
                }
            }
        } else {
            launch {
                _eventChannel.send(JonbeoCountEvent.StartAsset(assetId))
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