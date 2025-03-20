package com.sdhong.jonbeowin.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asset(
    @PrimaryKey val id: Int,
    val name: String,
    val dayCount: Int
)
