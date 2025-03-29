package com.sdhong.jonbeowin.core.domain.repository

import com.sdhong.jonbeowin.core.domain.model.Asset
import kotlinx.coroutines.flow.Flow

interface JonbeoRepository {

    fun getAllAssets(): Flow<List<Asset>>

    fun getAsset(assetId: Int): Flow<Asset>

    suspend fun updateAsset(asset: Asset)

    suspend fun delete(assetIds: Set<Int>)
}