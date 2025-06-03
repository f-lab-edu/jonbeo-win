package com.sdhong.jonbeowin.feature.jonbeocount.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdhong.jonbeowin.core.domain.usecase.DeleteAssetUseCase
import com.sdhong.jonbeowin.core.domain.usecase.GetAssetListUseCase
import com.sdhong.jonbeowin.feature.jonbeocount.model.BuyDateModel
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountModel
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.JonbeoCountUiState
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
    getAssetListUseCase: GetAssetListUseCase,
    private val deleteAssetUseCase: DeleteAssetUseCase
) : ViewModel() {

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
                        buyDate = BuyDateModel(
                            year = asset.buyDate.year,
                            month = asset.buyDate.month,
                            day = asset.buyDate.day
                        ),
                        createdAt = asset.createdAt,
                        isEditMode = isEditMode,
                        isChecked = if (isEditMode) checkedIdSet.contains(asset.id) else false
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
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = JonbeoCountUiState.Idle
    )

    private val _eventChannel = Channel<JonbeoCountEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()


    fun toggleEditMode() {
        if (uiState.value !is JonbeoCountUiState.Success) return

        viewModelScope.launch {
            val preValue = isEditMode.value
            if (preValue) {
                deleteAssetUseCase(checkedIdSet.value)
                checkedIdSet.value = emptySet()
            }
            isEditMode.value = !preValue
        }
    }

    fun onJonbeoCountItemClick(id: Int) {
        if (isEditMode.value) {
            checkedIdSet.value = checkedIdSet.value.toMutableSet().also { set ->
                if (set.contains(id)) {
                    set.remove(id)
                } else {
                    set.add(id)
                }
            }
        } else {
            viewModelScope.launch {
                _eventChannel.send(JonbeoCountEvent.StartAsset(id))
            }
        }
    }

    fun eventStartAddAsset() {
        viewModelScope.launch {
            _eventChannel.send(JonbeoCountEvent.StartAsset())
        }
    }

    sealed interface JonbeoCountEvent {
        data class StartAsset(val assetId: Int = 0) : JonbeoCountEvent
    }
}