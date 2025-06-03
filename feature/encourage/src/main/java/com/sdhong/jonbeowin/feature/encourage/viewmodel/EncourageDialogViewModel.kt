package com.sdhong.jonbeowin.feature.encourage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdhong.jonbeowin.core.domain.usecase.GenerateEncourageUseCase
import com.sdhong.jonbeowin.core.domain.usecase.UpdateEncourageUseCase
import com.sdhong.jonbeowin.feature.encourage.mapper.toDomain
import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageDialogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class EncourageDialogViewModel @Inject constructor(
    private val updateEncourageUseCase: UpdateEncourageUseCase,
    private val generateEncourageUseCase: GenerateEncourageUseCase
) : ViewModel() {

    private val encourageText = MutableStateFlow<String?>("")

    val uiState: StateFlow<EncourageDialogUiState> = encourageText.map { text ->
        when {
            text == null -> EncourageDialogUiState.Error
            text.isEmpty() -> EncourageDialogUiState.Loading
            else -> EncourageDialogUiState.Success(content = text)
        }
    }.catch {
        emit(EncourageDialogUiState.Error)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        EncourageDialogUiState.Loading
    )

    private val _eventChannel = Channel<EncourageDialogEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    init {
        generateEncourage()
    }

    fun saveEncourage() {
        if (uiState.value !is EncourageDialogUiState.Success) return

        viewModelScope.launch {
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
        viewModelScope.launch {
            encourageText.value = ""
            delay(300)
            encourageText.value = generateEncourageUseCase()
        }
    }

    fun eventDialogClose() {
        viewModelScope.launch {
            _eventChannel.send(EncourageDialogEvent.Close)
        }
    }

    sealed interface EncourageDialogEvent {
        data object Close : EncourageDialogEvent
    }
}