package com.sdhong.jonbeowin.core.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sdhong.jonbeowin.core.local.model.EncourageLocal
import com.sdhong.jonbeowin.core.local.room.RoomConstant

@Dao
interface EncourageDao {

    @Query("SELECT * FROM ${RoomConstant.Table.ENCOURAGE} ORDER BY createdAt DESC")
    suspend fun getAllEncourages(): List<EncourageLocal>

    @Upsert
    suspend fun update(encourage: EncourageLocal)

    @Query("DELETE FROM ${RoomConstant.Table.ENCOURAGE} WHERE id IN (:encourageIds)")
    suspend fun delete(encourageIds: Set<Int>)
}