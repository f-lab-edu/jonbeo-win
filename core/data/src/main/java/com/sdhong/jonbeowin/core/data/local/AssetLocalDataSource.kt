package com.sdhong.jonbeowin.core.data.local

import com.sdhong.jonbeowin.core.data.model.AssetEntity
import kotlinx.coroutines.flow.Flow

interface AssetLocalDataSource {

    fun getAllAssets(): Flow<List<AssetEntity>>

    suspend fun getAsset(assetId: Int): AssetEntity

    suspend fun updateAsset(asset: AssetEntity)

    suspend fun delete(assetIds: Set<Int>)
}