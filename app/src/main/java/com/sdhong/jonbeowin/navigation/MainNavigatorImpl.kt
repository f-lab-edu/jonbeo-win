package com.sdhong.jonbeowin.navigation

import android.content.Context
import android.content.Intent
import com.sdhong.jonbeowin.core.common.navigation.MainNavigator
import com.sdhong.jonbeowin.feature.asset.view.AssetActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainNavigatorImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : MainNavigator {

    override fun getAssetIntent(assetId: Int?): Intent {
        return AssetActivity.newIntent(
            context = context,
            assetId = assetId,
        )
    }
}