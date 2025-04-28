package com.sdhong.jonbeowin.core.data.mapper

import com.sdhong.jonbeowin.core.data.model.AssetEntity
import com.sdhong.jonbeowin.core.domain.model.Asset

internal fun AssetEntity.toDomain() =
    Asset(
        id = id,
        name = name,
        dayCount = dayCount,
        buyDate = buyDate.toDomain(),
        createdAt = createdAt
    )

internal fun Asset.toData() =
    AssetEntity(
        id = id,
        name = name,
        dayCount = dayCount,
        buyDate = buyDate.toData(),
        createdAt = createdAt
    )