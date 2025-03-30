package com.sdhong.jonbeowin.feature.encourage.model

data class EncourageModel(
    val id: Int,
    val content: String,
    val createdAt: String,
    val isEditMode: Boolean,
    val isChecked: Boolean
)
