package com.sdhong.jonbeowin.core.local.impl

import com.sdhong.jonbeowin.core.data.local.EncourageLocalDataSource
import com.sdhong.jonbeowin.core.data.model.EncourageEntity
import com.sdhong.jonbeowin.core.local.mapper.toData
import com.sdhong.jonbeowin.core.local.mapper.toLocal
import com.sdhong.jonbeowin.core.local.room.dao.EncourageDao
import javax.inject.Inject

internal class EncourageLocalDataSourceImpl @Inject constructor(
    private val encourageDao: EncourageDao
) : EncourageLocalDataSource {

    override suspend fun getAllEncourages(): List<EncourageEntity> =
        encourageDao.getAllEncourages().map { it.toData() }

    override suspend fun update(encourage: EncourageEntity) = encourageDao.update(encourage.toLocal())

    override suspend fun delete(encourageIds: Set<Int>) = encourageDao.delete(encourageIds)
}