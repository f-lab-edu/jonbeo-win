package com.sdhong.jonbeowin.feature.encourage.uistate

import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel

sealed interface EncourageUiState {

    data object Idle : EncourageUiState

    data object Empty : EncourageUiState

    data class Success(
        val encourageItemList: List<EncourageModel>,
        val isEditMode: Boolean
    ) : EncourageUiState

    data object Error : EncourageUiState
}
