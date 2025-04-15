package com.sdhong.jonbeowin.navigation.di

import com.sdhong.jonbeowin.core.common.navigation.MainNavigator
import com.sdhong.jonbeowin.navigation.MainNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
interface MainNavigatorModule {

    @Binds
    @ActivityScoped
    fun bindMainNavigator(navigator: MainNavigatorImpl): MainNavigator
}