package com.sdhong.jonbeowin.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sdhong.jonbeowin.local.model.Encourage
import kotlinx.coroutines.flow.Flow

@Dao
interface EncourageDao {

    @Query("SELECT * FROM encourage ORDER BY createdAt DESC")
    fun getAll(): Flow<List<Encourage>>

    @Upsert
    suspend fun update(encourage: Encourage)

    @Query("DELETE FROM encourage WHERE id IN (:encourageIds)")
    suspend fun delete(encourageIds: Set<Int>)
}