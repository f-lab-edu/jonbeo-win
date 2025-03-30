package com.sdhong.jonbeowin.jonbeocount.uistate

import androidx.annotation.StringRes
import com.sdhong.jonbeowin.jonbeocount.model.JonbeoCountModel

sealed interface JonbeoCountUiState {

    data object Idle : JonbeoCountUiState

    data object Empty : JonbeoCountUiState

    data class Success(
        val jonbeoCountItemList: List<JonbeoCountModel>,
        @StringRes val appBarButtonId: Int
    ) : JonbeoCountUiState

    data object Error : JonbeoCountUiState
}
