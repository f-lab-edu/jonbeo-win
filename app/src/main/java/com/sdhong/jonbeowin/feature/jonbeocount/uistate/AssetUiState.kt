package com.sdhong.jonbeowin.feature.jonbeocount.uistate

import com.sdhong.jonbeowin.local.model.Asset

data class AssetUiState(
    val asset: Asset,
    val isEditMode: Boolean,
    val isChecked: Boolean
)
