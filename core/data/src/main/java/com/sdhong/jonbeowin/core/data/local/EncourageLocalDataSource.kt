package com.sdhong.jonbeowin.core.data.local

import com.sdhong.jonbeowin.core.data.model.EncourageEntity

interface EncourageLocalDataSource {

    fun getAllEncourages(): List<EncourageEntity>

    suspend fun update(encourage: EncourageEntity)

    suspend fun delete(encourageIds: Set<Int>)
}