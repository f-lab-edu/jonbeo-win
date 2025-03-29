package com.sdhong.jonbeowin.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sdhong.jonbeowin.core.local.model.AssetLocal
import com.sdhong.jonbeowin.core.local.room.dao.AssetDao

@Database(
    entities = [AssetLocal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AssetDatabase : RoomDatabase() {

    abstract fun assetDao(): AssetDao
}