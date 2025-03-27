package com.sdhong.jonbeowin.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Encourage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String,
    val createdAt: String
)