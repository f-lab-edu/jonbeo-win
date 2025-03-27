package com.sdhong.jonbeowin.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sdhong.jonbeowin.local.dao.AssetDao
import com.sdhong.jonbeowin.local.model.Asset

@Database(
    entities = [Asset::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AssetDatabase : RoomDatabase() {

    abstract fun assetDao(): AssetDao
}