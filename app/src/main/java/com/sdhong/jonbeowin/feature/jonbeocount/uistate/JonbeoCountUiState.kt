package com.sdhong.jonbeowin.feature.jonbeocount.uistate

sealed interface JonbeoCountUiState {

    data object Idle : JonbeoCountUiState

    data object Empty : JonbeoCountUiState

    data class Success(
        val assetUiStateList: List<AssetUiState>,
        val isEditMode: Boolean
    ) : JonbeoCountUiState

    data object Error : JonbeoCountUiState
}
