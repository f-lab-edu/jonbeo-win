package com.sdhong.jonbeowin.core.local.model

data class AssetLocal(
    val id: Int,
    val name: String,
    val dayCount: Int,
    val buyDate: BuyDateLocal,
    val createdAt: String
)
