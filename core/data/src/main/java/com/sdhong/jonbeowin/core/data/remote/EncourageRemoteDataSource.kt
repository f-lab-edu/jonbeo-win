package com.sdhong.jonbeowin.core.data.remote

interface EncourageRemoteDataSource {

    suspend fun generateContent(): String?
}