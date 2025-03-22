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

        fun fromString(buyDateString: String): BuyDate {
            val date = buyDateString.split("/")
            return BuyDate(
                year = date[0].toInt(),
                month = date[1].toInt(),
                day = date[2].toInt()
            )
        }
    }
}