package com.sdhong.jonbeowin.navigation.di

import com.sdhong.jonbeowin.core.common.navigation.MainNavigator
import com.sdhong.jonbeowin.navigation.MainNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MainNavigatorModule {

    @Binds
    @Singleton
    fun bindMainNavigator(navigator: MainNavigatorImpl): MainNavigator
}