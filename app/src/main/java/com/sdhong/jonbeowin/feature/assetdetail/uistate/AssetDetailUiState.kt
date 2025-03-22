package com.sdhong.jonbeowin.feature.assetdetail.uistate

import com.sdhong.jonbeowin.feature.assetdetail.model.BuyDate
import com.sdhong.jonbeowin.local.model.Asset

sealed interface AssetDetailUiState {
    data object Idle : AssetDetailUiState
    data class Initial(val initialAsset: Asset) : AssetDetailUiState
    data class Success(val buyDate: BuyDate) : AssetDetailUiState
    data object Error : AssetDetailUiState
}