package com.sdhong.jonbeowin.local.database

import androidx.room.TypeConverter
import com.sdhong.jonbeowin.local.model.BuyDate

class Converters {

    @TypeConverter
    fun fromBuyDate(buyDate: BuyDate): String {
        return "${buyDate.year}/${buyDate.month}/${buyDate.day}"
    }

    @TypeConverter
    fun toBuyDate(buyDateString: String): BuyDate {
        val (year, month, day) = buyDateString.split("/").map { it.toInt() }
        return BuyDate(year, month, day)
    }
}