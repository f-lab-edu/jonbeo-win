package com.sdhong.jonbeowin.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sdhong.jonbeowin.local.model.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Query("SELECT * FROM asset ORDER BY createdAt DESC")
    fun getAll(): Flow<List<Asset>>

    @Query("SELECT * FROM asset WHERE id = :id")
    fun getAssetById(id: Int): Flow<Asset>

    @Upsert
    suspend fun update(asset: Asset)

    @Query("DELETE FROM asset WHERE id IN (:assetIds)")
    suspend fun delete(assetIds: Set<Int>)
}