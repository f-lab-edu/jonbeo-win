package com.sdhong.jonbeowin.core.data.model

data class AssetEntity(
    val id: Int,
    val name: String,
    val dayCount: Int,
    val buyDate: BuyDateEntity,
    val createdAt: String
)
