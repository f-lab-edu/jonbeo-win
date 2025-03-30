package com.sdhong.jonbeowin.feature.jonbeocount.model

data class JonbeoCountModel(
    val id: Int,
    val name: String,
    val dayCount: Int,
    val buyDate: BuyDateModel,
    val createdAt: String,
    val isEditMode: Boolean,
    val isChecked: Boolean
)
