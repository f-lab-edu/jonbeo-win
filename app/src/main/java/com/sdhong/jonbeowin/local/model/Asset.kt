package com.sdhong.jonbeowin.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdhong.jonbeowin.feature.asset.model.BuyDate

@Entity
data class Asset(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val dayCount: Int,
    val buyDate: BuyDate,
    val generatedTime: String
) {
    companion object {
        val Default = Asset(
            id = 0,
            name = "",
            dayCount = 0,
            buyDate = BuyDate.Default,
            generatedTime = ""
        )
    }
}
