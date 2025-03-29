package com.sdhong.jonbeowin.core.data.local

import com.sdhong.jonbeowin.core.data.model.EncourageEntity

interface EncourageLocalDataSource {

    suspend fun getAllEncourages(): List<EncourageEntity>

    suspend fun update(encourage: EncourageEntity)

    suspend fun delete(encourageIds: Set<Int>)
}