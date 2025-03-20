package com.sdhong.jonbeowin.local.model

data class BuyDate(
    val year: Int,
    val month: Int,
    val day: Int
) {
    companion object {
        val Default = BuyDate(
            year = 0,
            month = 0,
            day = 0
        )
    }
}