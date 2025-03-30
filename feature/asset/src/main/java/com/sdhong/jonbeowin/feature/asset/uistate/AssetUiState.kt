package com.sdhong.jonbeowin.feature.asset.uistate

import com.sdhong.jonbeowin.feature.asset.model.AssetModel
import com.sdhong.jonbeowin.feature.asset.model.BuyDateModel

sealed interface AssetUiState {

    data object Idle : AssetUiState

    data class AssetDetailInitial(val initialAsset: AssetModel) : AssetUiState
    data object AddAssetInitial : AssetUiState

    data class AssetDateSelected(val buyDate: BuyDateModel) : AssetUiState

    data object Error : AssetUiState
}