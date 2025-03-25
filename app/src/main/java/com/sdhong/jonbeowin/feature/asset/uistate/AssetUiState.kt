package com.sdhong.jonbeowin.feature.asset.uistate

import com.sdhong.jonbeowin.feature.asset.model.BuyDate
import com.sdhong.jonbeowin.local.model.Asset

sealed interface AssetUiState {

    data object Idle : AssetUiState

    data class AssetDetailInitial(val initialAsset: Asset) : AssetUiState
    data object AddAssetInitial : AssetUiState

    data class AssetDetailDateSelected(val buyDate: BuyDate) : AssetUiState
    data class AddAssetDateSelected(val buyDate: BuyDate) : AssetUiState

    data object Error : AssetUiState
}