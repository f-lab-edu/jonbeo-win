package com.sdhong.jonbeowin.core.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sdhong.jonbeowin.core.local.model.AssetLocal

@Dao
interface AssetDao {

    @Query("SELECT * FROM asset ORDER BY createdAt DESC")
    suspend fun getAllAssets(): List<AssetLocal>

    @Query("SELECT * FROM asset WHERE id = :assetId")
    suspend fun getAsset(assetId: Int): AssetLocal

    @Upsert
    suspend fun update(asset: AssetLocal)

    @Query("DELETE FROM asset WHERE id IN (:assetIds)")
    suspend fun delete(assetIds: Set<Int>)
}