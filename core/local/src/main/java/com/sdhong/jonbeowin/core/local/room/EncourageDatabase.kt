package com.sdhong.jonbeowin.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdhong.jonbeowin.core.local.model.EncourageLocal
import com.sdhong.jonbeowin.core.local.room.dao.EncourageDao

@Database(
    entities = [EncourageLocal::class],
    version = 1,
    exportSchema = false
)
abstract class EncourageDatabase : RoomDatabase() {

    abstract fun encourageDao(): EncourageDao
}