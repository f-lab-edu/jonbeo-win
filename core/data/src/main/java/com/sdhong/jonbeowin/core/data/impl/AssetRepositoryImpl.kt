package com.sdhong.jonbeowin.core.data.impl

import com.sdhong.jonbeowin.core.data.local.AssetLocalDataSource
import com.sdhong.jonbeowin.core.data.mapper.toData
import com.sdhong.jonbeowin.core.data.mapper.toDomain
import com.sdhong.jonbeowin.core.domain.model.Asset
import com.sdhong.jonbeowin.core.domain.repository.AssetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AssetRepositoryImpl @Inject constructor(
    private val assetLocalDataSource: AssetLocalDataSource,
) : AssetRepository {

    override fun getAllAssets(): Flow<List<Asset>> =
        assetLocalDataSource.getAllAssets().map { list ->
            list.map {
                it.toDomain()
            }
        }

    override fun getAsset(assetId: Int): Flow<Asset> = assetLocalDataSource.getAsset(assetId).map { it.toDomain() }

    override suspend fun updateAsset(asset: Asset) = assetLocalDataSource.updateAsset(asset.toData())

    override suspend fun delete(assetIds: Set<Int>) = assetLocalDataSource.delete(assetIds)
}