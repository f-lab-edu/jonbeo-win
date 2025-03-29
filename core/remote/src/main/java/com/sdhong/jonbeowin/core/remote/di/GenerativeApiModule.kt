package com.sdhong.jonbeowin.core.remote.di

import com.google.ai.client.generativeai.GenerativeModel
import com.sdhong.core.remote.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object GenerativeApiModule {

    @Singleton
    @Provides
    fun provideGenerativeApi(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }
}