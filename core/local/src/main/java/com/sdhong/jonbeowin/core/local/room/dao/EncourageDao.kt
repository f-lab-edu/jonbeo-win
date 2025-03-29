package com.sdhong.jonbeowin.core.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sdhong.jonbeowin.core.local.model.EncourageLocal

@Dao
interface EncourageDao {

    @Query("SELECT * FROM encourage ORDER BY createdAt DESC")
    suspend fun getAllEncourages(): List<EncourageLocal>

    @Upsert
    suspend fun update(encourage: EncourageLocal)

    @Query("DELETE FROM encourage WHERE id IN (:encourageIds)")
    suspend fun delete(encourageIds: Set<Int>)
}