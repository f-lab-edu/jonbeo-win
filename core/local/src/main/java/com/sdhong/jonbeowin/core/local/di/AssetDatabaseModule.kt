package com.sdhong.jonbeowin.core.local.di

import android.content.Context
import androidx.room.Room
import com.sdhong.jonbeowin.core.local.room.AssetDatabase
import com.sdhong.jonbeowin.core.local.room.dao.AssetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AssetDatabaseModule {

    @Singleton
    @Provides
    fun provideAssetDao(database: AssetDatabase): AssetDao = database.assetDao()

    @Singleton
    @Provides
    fun provideAssetDatabase(@ApplicationContext context: Context): AssetDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AssetDatabase::class.java,
            "asset-database"
        ).build()
    }
}