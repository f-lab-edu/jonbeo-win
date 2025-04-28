package com.sdhong.jonbeowin.core.domain.model

data class Asset(
    val id: Int,
    val name: String,
    val dayCount: Int,
    val buyDate: BuyDate,
    val createdAt: String
)
