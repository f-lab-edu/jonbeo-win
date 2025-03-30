package com.sdhong.jonbeowin.feature.asset.mapper

import com.sdhong.jonbeowin.core.domain.model.Asset
import com.sdhong.jonbeowin.core.domain.model.BuyDate
import com.sdhong.jonbeowin.feature.asset.model.AssetModel
import com.sdhong.jonbeowin.feature.asset.model.BuyDateModel

internal fun AssetModel.toDomain() =
    Asset(
        id = id,
        name = name,
        dayCount = dayCount,
        buyDate = BuyDate(
            year = buyDate.year,
            month = buyDate.month,
            day = buyDate.day
        ),
        createdAt = createdAt
    )

internal fun Asset.toPresentation() =
    AssetModel(
        id = id,
        name = name,
        dayCount = dayCount,
        buyDate = BuyDateModel(
            year = buyDate.year,
            month = buyDate.month,
            day = buyDate.day
        ),
        createdAt = createdAt
    )