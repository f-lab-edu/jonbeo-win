package com.sdhong.jonbeowin.feature.asset.model

data class AssetModel(
    val id: Int,
    val name: String,
    val dayCount: Int,
    val buyDate: BuyDateModel,
    val createdAt: String
) {
    companion object {
        val Default = AssetModel(
            id = 0,
            name = "",
            dayCount = 0,
            buyDate = BuyDateModel.Default,
            createdAt = ""
        )
    }
}