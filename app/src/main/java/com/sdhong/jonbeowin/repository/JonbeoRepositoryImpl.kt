package com.sdhong.jonbeowin.repository

import com.sdhong.jonbeowin.local.dao.AssetDao
import com.sdhong.jonbeowin.local.model.Asset
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JonbeoRepositoryImpl @Inject constructor(
    private val assetDao: AssetDao
) : JonbeoRepository {

    override fun flowAllAssets(): Flow<List<Asset>> = assetDao.getAll()

    override fun flowAssetById(id: Int): Flow<Asset> = assetDao.getAssetById(id)

    override suspend fun update(asset: Asset) = assetDao.update(asset)

    override suspend fun delete(assetIds: Set<Int>) = assetDao.delete(assetIds)
}