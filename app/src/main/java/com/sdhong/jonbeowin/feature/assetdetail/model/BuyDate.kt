package com.sdhong.jonbeowin.feature.assetdetail.model

data class BuyDate(
    val year: Int,
    val month: Int,
    val day: Int
) {
    val formattedString: String = "$year/$month/$day"

    companion object {

        val Default = BuyDate(
            year = 0,
            month = 0,
            day = 0
        )
    }
}