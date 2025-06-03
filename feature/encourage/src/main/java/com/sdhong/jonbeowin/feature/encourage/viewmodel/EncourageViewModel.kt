package com.sdhong.jonbeowin.feature.encourage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdhong.jonbeowin.core.domain.usecase.DeleteEncourageUseCase
import com.sdhong.jonbeowin.core.domain.usecase.GetEncourageListUseCase
import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageUiState
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
class EncourageViewModel @Inject constructor(
    getEncourageListUseCase: GetEncourageListUseCase,
    private val deleteEncourageUseCase: DeleteEncourageUseCase
) : ViewModel() {

    private val isEditMode = MutableStateFlow(false)
    private val checkedIdSet = MutableStateFlow<Set<Int>>(emptySet())

    val uiState: StateFlow<EncourageUiState> = combine(
        getEncourageListUseCase(),
        isEditMode,
        checkedIdSet
    ) { encourageList, isEditMode, checkedIdSet ->
        if (encourageList.isNotEmpty()) {
            EncourageUiState.Success(
                encourageItemList = encourageList.map { encourage ->
                    EncourageModel(
                        id = encourage.id,
                        content = encourage.content,
                        createdAt = encourage.createdAt,
                        isEditMode = isEditMode,
                        isChecked = if (isEditMode) checkedIdSet.contains(encourage.id) else false
                    )
                },
                isEditMode = isEditMode
            )
        } else {
            EncourageUiState.Empty
        }
    }.catch {
        emit(EncourageUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = EncourageUiState.Idle
    )

    private val _eventChannel = Channel<EncourageEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()


    fun toggleEditMode() {
        if (uiState.value !is EncourageUiState.Success) return

        viewModelScope.launch {
            val preValue = isEditMode.value
            if (preValue) {
                deleteEncourageUseCase(checkedIdSet.value)
                checkedIdSet.value = emptySet()
            }
            isEditMode.value = !preValue
        }
    }

    fun onEncourageItemClick(id: Int) {
        if (!isEditMode.value) return

        checkedIdSet.value = checkedIdSet.value.toMutableSet().also { set ->
            if (set.contains(id)) {
                set.remove(id)
            } else {
                set.add(id)
            }
        }
    }

    fun eventShowEncourageDialog() {
        viewModelScope.launch {
            _eventChannel.send(EncourageEvent.ShowEncourageDialog)
        }
    }

    sealed interface EncourageEvent {
        data object ShowEncourageDialog : EncourageEvent
    }
}