package com.sdhong.jonbeowin.feature.jonbeocount.uistate

import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountItem

sealed interface JonbeoCountUiState {

    data object Idle : JonbeoCountUiState

    data object Empty : JonbeoCountUiState

    data class Success(
        val jonbeoCountItemList: List<JonbeoCountItem>,
        val isEditMode: Boolean
    ) : JonbeoCountUiState

    data object Error : JonbeoCountUiState
}
