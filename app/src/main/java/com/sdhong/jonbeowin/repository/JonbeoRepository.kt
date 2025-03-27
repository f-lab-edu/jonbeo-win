package com.sdhong.jonbeowin.repository

import com.sdhong.jonbeowin.local.model.Asset
import kotlinx.coroutines.flow.Flow

interface JonbeoRepository {

    fun flowAllAssets(): Flow<List<Asset>>

    fun flowAssetById(id: Int): Flow<Asset>

    suspend fun update(asset: Asset)

    suspend fun delete(assetIds: Set<Int>)
}