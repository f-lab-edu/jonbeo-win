package com.sdhong.jonbeowin.core.local.di

import com.sdhong.jonbeowin.core.data.local.AssetLocalDataSource
import com.sdhong.jonbeowin.core.data.local.EncourageLocalDataSource
import com.sdhong.jonbeowin.core.local.impl.AssetLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface LocalDataSourceModule {

    @Binds
    @Singleton
    fun bindAssetLocalDataResource(source: AssetLocalDataSourceImpl): AssetLocalDataSource

    @Binds
    @Singleton
    fun bindEncourageLocalDataSource(source: EncourageLocalDataSourceImpl): EncourageLocalDataSource
}
