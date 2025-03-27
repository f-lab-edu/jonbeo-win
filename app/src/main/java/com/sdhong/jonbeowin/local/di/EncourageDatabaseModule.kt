package com.sdhong.jonbeowin.local.di

import android.content.Context
import androidx.room.Room
import com.sdhong.jonbeowin.local.dao.EncourageDao
import com.sdhong.jonbeowin.local.database.EncourageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncourageDatabaseModule {

    @Singleton
    @Provides
    fun provideEncourageDao(database: EncourageDatabase): EncourageDao = database.encourageDao()

    @Singleton
    @Provides
    fun provideEncourageDatabase(@ApplicationContext context: Context): EncourageDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            EncourageDatabase::class.java,
            "encourage-database"
        ).build()
    }
}