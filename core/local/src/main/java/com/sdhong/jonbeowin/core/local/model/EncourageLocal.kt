package com.sdhong.jonbeowin.core.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdhong.jonbeowin.core.local.room.RoomConstant

@Entity(tableName = RoomConstant.Table.ENCOURAGE)
data class EncourageLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val content: String,
    val createdAt: String
)