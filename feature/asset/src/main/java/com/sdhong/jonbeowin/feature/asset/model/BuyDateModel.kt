package com.sdhong.jonbeowin.feature.asset.model

data class BuyDateModel(
    val year: Int,
    val month: Int,
    val day: Int
) {
    companion object {
        val Default = BuyDateModel(
            year = 0,
            month = 0,
            day = 0
        )
    }
}
