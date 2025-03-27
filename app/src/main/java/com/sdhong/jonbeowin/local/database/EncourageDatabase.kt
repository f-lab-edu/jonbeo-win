package com.sdhong.jonbeowin.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdhong.jonbeowin.local.dao.EncourageDao
import com.sdhong.jonbeowin.local.model.Encourage

@Database(
    entities = [Encourage::class],
    version = 1,
    exportSchema = false
)
abstract class EncourageDatabase : RoomDatabase() {

    abstract fun encourageDao(): EncourageDao
}