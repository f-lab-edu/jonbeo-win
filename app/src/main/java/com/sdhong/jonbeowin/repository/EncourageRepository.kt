package com.sdhong.jonbeowin.repository

import com.sdhong.jonbeowin.local.model.Encourage
import kotlinx.coroutines.flow.Flow

interface EncourageRepository {

    fun flowAllEncourages(): Flow<List<Encourage>>
    suspend fun update(encourage: Encourage)
    suspend fun delete(encourageIds: Set<Int>)

    suspend fun generateContent(): String?
}