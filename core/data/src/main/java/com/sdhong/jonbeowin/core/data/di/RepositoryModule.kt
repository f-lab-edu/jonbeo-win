package com.sdhong.jonbeowin.core.data.di

import com.sdhong.jonbeowin.core.data.impl.AssetRepositoryImpl
import com.sdhong.jonbeowin.core.data.impl.EncourageRepositoryImpl
import com.sdhong.jonbeowin.core.domain.repository.AssetRepository
import com.sdhong.jonbeowin.core.domain.repository.EncourageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindAssetRepository(repo: AssetRepositoryImpl): AssetRepository

    @Binds
    @Singleton
    fun bindEncourageRepository(repo: EncourageRepositoryImpl): EncourageRepository
}