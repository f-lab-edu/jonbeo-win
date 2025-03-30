package com.sdhong.jonbeowin.core.data.di

import com.sdhong.jonbeowin.core.data.impl.EncourageRepositoryImpl
import com.sdhong.jonbeowin.core.data.impl.JonbeoRepositoryImpl
import com.sdhong.jonbeowin.core.domain.repository.EncourageRepository
import com.sdhong.jonbeowin.core.domain.repository.JonbeoRepository
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
    fun bindJonbeoRepository(repo: JonbeoRepositoryImpl): JonbeoRepository

    @Binds
    @Singleton
    fun bindEncourageRepository(repo: EncourageRepositoryImpl): EncourageRepository
}