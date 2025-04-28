package com.sdhong.jonbeowin.core.data.local

import com.sdhong.jonbeowin.core.data.model.EncourageEntity
import kotlinx.coroutines.flow.Flow

interface EncourageLocalDataSource {

    fun getAllEncourages(): Flow<List<EncourageEntity>>

    suspend fun update(encourage: EncourageEntity)

    suspend fun delete(encourageIds: Set<Int>)
}