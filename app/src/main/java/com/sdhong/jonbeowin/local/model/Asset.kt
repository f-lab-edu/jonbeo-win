package com.sdhong.jonbeowin.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asset(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val dayCount: Int,
    val buyDateString: String,
    val generatedTime: String
)
