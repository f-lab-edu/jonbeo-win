package com.sdhong.jonbeowin.feature.encourage.uistate

import androidx.annotation.StringRes
import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel

sealed interface EncourageUiState {

    data object Idle : EncourageUiState

    data object Empty : EncourageUiState

    data class Success(
        val encourageItemList: List<EncourageModel>,
        @StringRes val appBarButtonId: Int
    ) : EncourageUiState

    data object Error : EncourageUiState
}
