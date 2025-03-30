package com.sdhong.jonbeowin.feature.encourage.viewmodel

import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.common.base.BaseViewModel
import com.sdhong.jonbeowin.core.domain.usecase.DeleteEncourageUseCase
import com.sdhong.jonbeowin.core.domain.usecase.GetEncourageListUseCase
import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageUiState
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
class EncourageViewModel @Inject constructor(
    getEncourageListUseCase: GetEncourageListUseCase,
    private val deleteEncourageUseCase: DeleteEncourageUseCase
) : BaseViewModel() {

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
                appBarButtonId = if (isEditMode) R.string.remove else R.string.edit
            )
        } else {
            EncourageUiState.Empty
        }
    }.catch {
        emit(EncourageUiState.Error)
    }.stateIn(
        initialValue = EncourageUiState.Idle
    )

    private val _eventChannel = Channel<EncourageEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()


    fun toggleEditMode() {
        if (uiState.value !is EncourageUiState.Success) return

        launch {
            val preValue = isEditMode.value
            if (preValue) {
                deleteEncourageUseCase(checkedIdSet.value)
                checkedIdSet.value = emptySet()
            }
            isEditMode.value = !preValue
        }
    }

    fun onEncourageItemClick(position: Int) {
        if (!isEditMode.value) return
        val encourageId = (uiState.value as? EncourageUiState.Success ?: return)
            .encourageItemList[position]
            .id

        checkedIdSet.value = checkedIdSet.value.toMutableSet().also { set ->
            if (set.contains(encourageId)) {
                set.remove(encourageId)
            } else {
                set.add(encourageId)
            }
        }
    }

    fun eventShowEncourageDialog() {
        launch {
            _eventChannel.send(EncourageEvent.ShowEncourageDialog)
        }
    }

    sealed interface EncourageEvent {
        data object ShowEncourageDialog : EncourageEvent
    }
}