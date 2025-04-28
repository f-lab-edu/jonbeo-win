package com.sdhong.jonbeowin.core.remote.di

import com.sdhong.jonbeowin.core.data.remote.EncourageRemoteDataSource
import com.sdhong.jonbeowin.core.remote.impl.EncourageRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RemoteDataSourceModule {

    @Binds
    @Singleton
    fun bindEncourageRemoteDataSource(source: EncourageRemoteDataSourceImpl): EncourageRemoteDataSource

}
