package com.sdhong.jonbeowin.feature.asset.uistate

import com.sdhong.jonbeowin.feature.asset.model.AssetModel

sealed interface AssetUiState {

    data object Idle : AssetUiState

    data class Success(val asset: AssetModel) : AssetUiState

    data object Error : AssetUiState
}