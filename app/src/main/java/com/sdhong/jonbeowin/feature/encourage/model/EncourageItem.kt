package com.sdhong.jonbeowin.feature.encourage.model

import com.sdhong.jonbeowin.local.model.Encourage

data class EncourageItem(
    val encourage: Encourage,
    val isEditMode: Boolean,
    val isChecked: Boolean
)
