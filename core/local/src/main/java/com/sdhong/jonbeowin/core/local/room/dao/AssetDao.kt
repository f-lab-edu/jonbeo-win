package com.sdhong.jonbeowin.core.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sdhong.jonbeowin.core.local.model.AssetLocal
import com.sdhong.jonbeowin.core.local.room.RoomConstant
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Query("SELECT * FROM ${RoomConstant.Table.ASSET} ORDER BY createdAt DESC")
    fun getAllAssets(): Flow<List<AssetLocal>>

    @Query("SELECT * FROM ${RoomConstant.Table.ASSET} WHERE id = :assetId")
    fun getAsset(assetId: Int): Flow<AssetLocal>

    @Upsert
    suspend fun update(asset: AssetLocal)

    @Query("DELETE FROM ${RoomConstant.Table.ASSET} WHERE id IN (:assetIds)")
    suspend fun delete(assetIds: Set<Int>)
}