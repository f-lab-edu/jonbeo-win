package com.sdhong.jonbeowin

import android.app.Application
import timber.log.Timber

class JonbeoWinApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}