package com.sdhong.jonbeowin.core.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdhong.jonbeowin.core.local.room.RoomConstant

@Entity(tableName = RoomConstant.Table.ASSET)
data class AssetLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val dayCount: Int,
    val buyDate: BuyDateLocal,
    val createdAt: String
)
