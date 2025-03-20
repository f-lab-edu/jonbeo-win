package com.sdhong.jonbeowin.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdhong.jonbeowin.local.dao.AssetDao
import com.sdhong.jonbeowin.local.model.Asset

@Database(
    entities = [Asset::class],
    version = 1,
    exportSchema = false
)
abstract class AssetDatabase : RoomDatabase() {

    abstract fun assetDao(): AssetDao
}