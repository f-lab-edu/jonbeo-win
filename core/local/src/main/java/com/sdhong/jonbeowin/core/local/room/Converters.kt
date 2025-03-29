package com.sdhong.jonbeowin.core.local.room

import androidx.room.TypeConverter
import com.sdhong.jonbeowin.core.local.model.BuyDateLocal

class Converters {

    @TypeConverter
    fun fromBuyDate(buyDate: BuyDateLocal): String {
        return "${buyDate.year}/${buyDate.month}/${buyDate.day}"
    }

    @TypeConverter
    fun toBuyDate(buyDateString: String): BuyDateLocal {
        val (year, month, day) = buyDateString.split("/").map { it.toInt() }
        return BuyDateLocal(year, month, day)
    }
}