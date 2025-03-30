package com.sdhong.jonbeowin.feature.jonbeocount.uistate

import androidx.annotation.StringRes
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountModel

sealed interface JonbeoCountUiState {

    data object Idle : JonbeoCountUiState

    data object Empty : JonbeoCountUiState

    data class Success(
        val jonbeoCountItemList: List<JonbeoCountModel>,
        val isEditMode: Boolean
    ) : JonbeoCountUiState

    data object Error : JonbeoCountUiState
}
