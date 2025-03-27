package com.sdhong.jonbeowin.feature.jonbeocount.uistate

import androidx.annotation.StringRes
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountItem

sealed interface JonbeoCountUiState {

    data object Idle : JonbeoCountUiState

    data object Empty : JonbeoCountUiState

    data class Success(
        val jonbeoCountItemList: List<JonbeoCountItem>,
        @StringRes val appBarButtonId: Int
    ) : JonbeoCountUiState

    data object Error : JonbeoCountUiState
}
