package com.sdhong.jonbeowin.core.data.local

import com.sdhong.jonbeowin.core.data.model.AssetEntity

interface AssetLocalDataSource {

    fun getAllAssets(): List<AssetEntity>

    fun getAsset(assetId: Int): AssetEntity

    suspend fun updateAsset(asset: AssetEntity)

    suspend fun delete(assetIds: Set<Int>)
}