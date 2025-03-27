package com.sdhong.jonbeowin.repository.di

import com.sdhong.jonbeowin.repository.EncourageRepository
import com.sdhong.jonbeowin.repository.EncourageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface EncourageRepositoryModule {

    @Binds
    @Singleton
    fun bindEncourageRepository(repository: EncourageRepositoryImpl): EncourageRepository
}