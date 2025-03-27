package com.sdhong.jonbeowin.feature.encourage.viewmodel

import android.icu.util.Calendar
import com.sdhong.jonbeowin.base.BaseViewModel
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageDialogUiState
import com.sdhong.jonbeowin.local.model.Encourage
import com.sdhong.jonbeowin.repository.EncourageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EncourageDialogViewModel @Inject constructor(
    private val encourageRepository: EncourageRepository
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
            encourageRepository.update(
                Encourage(
                    content = encourageText.value!!,
                    createdAt = Calendar.getInstance().time.toString()
                )
            )
        }
        eventDialogClose()
    }

    fun generateEncourage() {
        launch {
            encourageText.value = ""
            delay(300)
            encourageText.value = encourageRepository.generateContent()?.trim()
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