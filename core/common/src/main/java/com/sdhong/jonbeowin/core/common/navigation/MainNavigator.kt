package com.sdhong.jonbeowin.core.common.navigation

import androidx.navigation.NavController

interface MainNavigator {

    fun navigateAsset(navController: NavController, assetId: Int)
}