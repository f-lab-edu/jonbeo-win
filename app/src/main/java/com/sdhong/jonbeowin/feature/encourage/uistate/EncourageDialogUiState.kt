package com.sdhong.jonbeowin.feature.encourage.uistate

sealed interface EncourageDialogUiState {
    data object Loading : EncourageDialogUiState
    data class Success(val content: String) : EncourageDialogUiState
    data object Error : EncourageDialogUiState
}