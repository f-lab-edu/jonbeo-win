package com.sdhong.jonbeowin.view.uistate

import com.sdhong.jonbeowin.local.model.Asset

data class AssetUiState(
    val asset: Asset,
    val isEditMode: Boolean,
    val isChecked: Boolean
)
