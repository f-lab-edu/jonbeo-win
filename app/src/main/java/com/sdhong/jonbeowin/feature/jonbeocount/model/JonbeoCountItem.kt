package com.sdhong.jonbeowin.feature.jonbeocount.model

import com.sdhong.jonbeowin.local.model.Asset

data class JonbeoCountItem(
    val asset: Asset,
    val isEditMode: Boolean,
    val isChecked: Boolean
)
