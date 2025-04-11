package com.sdhong.jonbeowin.core.common.navigation

import android.content.Intent

interface MainNavigator {
    fun getAssetIntent(assetId: Int?): Intent
}