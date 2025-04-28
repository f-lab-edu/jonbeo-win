package com.sdhong.jonbeowin.feature.encourage.viewmodel

import com.sdhong.jonbeowin.core.common.base.BaseViewModel
import com.sdhong.jonbeowin.core.domain.usecase.GenerateEncourageUseCase
import com.sdhong.jonbeowin.core.domain.usecase.UpdateEncourageUseCase
import com.sdhong.jonbeowin.feature.encourage.mapper.toDomain
import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageDialogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class EncourageDialogViewModel @Inject constructor(
    private val updateEncourageUseCase: UpdateEncourageUseCase,
    private val generateEncourageUseCase: GenerateEncourageUseCase
) : BaseViewModel() {

    private val encourageText = MutableStateFlow<String?>("")

    val uiState: StateFlow<EncourageDialogUiState> = encourageText.map { text ->
        when {
            text == null -> EncourageDialogUiState.Error
            text.isEmpty() -> EncourageDialogUiState.Loading
            else -> EncourageDialogUiState.Success(content = text)
        }
    }.catch {
        emit(EncourageDialogUiState.Error)
    }.stateIn(EncourageDialogUiState.Loading)

    private val _eventChannel = Channel<EncourageDialogEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    init {
        generateEncourage()
    }

    fun saveEncourage() {
        if (uiState.value !is EncourageDialogUiState.Success) return

        launch {
            updateEncourageUseCase(
                EncourageModel(
                    id = 0,
                    content = encourageText.value!!,
                    createdAt = Calendar.getInstance().time.toString(),
                    isEditMode = false,
                    isChecked = false
                ).toDomain()
            )
        }
        eventDialogClose()
    }

    fun generateEncourage() {
        launch {
            encourageText.value = ""
            delay(300)
            encourageText.value = generateEncourageUseCase()
        }
    }

    fun eventDialogClose() {
        launch {
            _eventChannel.send(EncourageDialogEvent.Close)
        }
    }

    sealed interface EncourageDialogEvent {
        data object Close : EncourageDialogEvent
    }
}