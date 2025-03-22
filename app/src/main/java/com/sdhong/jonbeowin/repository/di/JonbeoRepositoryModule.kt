package com.sdhong.jonbeowin.repository.di

import com.sdhong.jonbeowin.repository.JonbeoRepository
import com.sdhong.jonbeowin.repository.JonbeoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface JonbeoRepositoryModule {

    @Binds
    @Singleton
    fun bindJonbeoRepository(repository: JonbeoRepositoryImpl): JonbeoRepository
}