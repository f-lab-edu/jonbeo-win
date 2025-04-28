package com.sdhong.jonbeowin.navigation

import androidx.navigation.NavDirections
import com.sdhong.jonbeowin.core.common.navigation.MainNavigator
import com.sdhong.jonbeowin.feature.jonbeocount.view.JonbeoCountFragmentDirections
import javax.inject.Inject

class MainNavigatorImpl @Inject constructor() : MainNavigator {

    override fun getAssetDirections(assetId: Int): NavDirections =
        JonbeoCountFragmentDirections.actionJonbeoCountToAsset(assetId)
}

