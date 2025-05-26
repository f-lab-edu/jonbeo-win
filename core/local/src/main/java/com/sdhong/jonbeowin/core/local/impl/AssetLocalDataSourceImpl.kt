package com.sdhong.jonbeowin.core.local.impl

import com.sdhong.jonbeowin.core.data.local.AssetLocalDataSource
import com.sdhong.jonbeowin.core.data.model.AssetEntity
import com.sdhong.jonbeowin.core.local.mapper.toData
import com.sdhong.jonbeowin.core.local.mapper.toLocal
import com.sdhong.jonbeowin.core.local.room.dao.AssetDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AssetLocalDataSourceImpl @Inject constructor(
    private val assetDao: AssetDao
) : AssetLocalDataSource {

    override fun getAllAssets(): Flow<List<AssetEntity>> =
        assetDao.getAllAssets().map { list ->
            list.map { it.toData() }
        }

    override fun getAsset(assetId: Int): Flow<AssetEntity> = assetDao.getAsset(assetId).map { it.toData() }

    override suspend fun updateAsset(asset: AssetEntity) = assetDao.update(asset.toLocal())

    override suspend fun delete(assetIds: Set<Int>) = assetDao.delete(assetIds)
}