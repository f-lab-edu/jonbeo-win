package com.sdhong.jonbeowin.feature.asset.enum

import androidx.annotation.StringRes
import com.sdhong.jonbeowin.feature.asset.R

enum class AssetToast(@StringRes val messageId: Int) {
    ASSET_NAME_EMPTY(R.string.asset_name_empty_message),
    ASSET_BUY_DATE_EMPTY(R.string.date_empty_message),
    ASSET_BUY_DATE_INVALID(R.string.date_error_message)
}