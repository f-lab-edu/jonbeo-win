package com.sdhong.jonbeowin.feature.encourage.viewmodel

import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseViewModel
import com.sdhong.jonbeowin.feature.encourage.model.EncourageItem
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageUiState
import com.sdhong.jonbeowin.local.model.Encourage
import com.sdhong.jonbeowin.repository.EncourageRepository
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
    private val encourageRepository: EncourageRepository
) : BaseViewModel() {

    private val isEditMode = MutableStateFlow(false)
    private val checkedMap = MutableStateFlow<Map<Int, Boolean>>(emptyMap())

    val uiState: StateFlow<EncourageUiState> = combine(
        encourageRepository.flowAllEncourages(),
        isEditMode,
        checkedMap
    ) { encourageList, isEditMode, checkedMap ->
        if (encourageList.isNotEmpty()) {
            EncourageUiState.Success(
                encourageItemList = encourageList.map { encourage ->
                    EncourageItem(
                        encourage = encourage,
                        isEditMode = isEditMode,
                        isChecked = if (isEditMode) (checkedMap[encourage.id] ?: false) else false
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
                val checkedIdSet = checkedMap.value.keys
                encourageRepository.delete(checkedIdSet)
                checkedMap.value = emptyMap()
            }
            isEditMode.value = !preValue
        }
    }

    fun onEncourageItemClick(encourage: Encourage) {
        if (!isEditMode.value) return

        checkedMap.value = checkedMap.value.toMutableMap().also { map ->
            val currentValue = map[encourage.id] ?: false
            map[encourage.id] = !currentValue
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