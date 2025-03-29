package com.sdhong.jonbeowin.core.domain.repository

import com.sdhong.jonbeowin.core.domain.model.Encourage
import kotlinx.coroutines.flow.Flow

interface EncourageRepository {

    fun getAllEncourages(): Flow<List<Encourage>>

    suspend fun update(encourage: Encourage)

    suspend fun delete(encourageIds: Set<Int>)

    suspend fun generateContent(): String?
}