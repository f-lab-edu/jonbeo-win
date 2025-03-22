package com.sdhong.jonbeowin.feature.addasset.uistate

import com.sdhong.jonbeowin.local.model.BuyDate

sealed interface AddAssetUiState {
    data object Idle : AddAssetUiState
    data class Success(val buyDate: BuyDate) : AddAssetUiState
    data object Error : AddAssetUiState
}