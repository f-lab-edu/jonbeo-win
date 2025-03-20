package com.sdhong.jonbeowin.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sdhong.jonbeowin.local.model.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Query("SELECT * FROM asset ORDER BY generatedTime DESC")
    fun getAll(): Flow<List<Asset>>

    @Upsert
    suspend fun update(asset: Asset)
}