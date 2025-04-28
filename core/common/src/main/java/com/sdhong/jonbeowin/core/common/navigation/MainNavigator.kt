package com.sdhong.jonbeowin.core.common.navigation

import androidx.navigation.NavDirections

interface MainNavigator {

    fun getAssetDirections(assetId: Int): NavDirections
}