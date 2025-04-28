package com.sdhong.jonbeowin.core.local.mapper

import com.sdhong.jonbeowin.core.data.model.AssetEntity
import com.sdhong.jonbeowin.core.local.model.AssetLocal

internal fun AssetLocal.toData() =
    AssetEntity(
        id = id,
        name = name,
        dayCount = dayCount,
        buyDate = buyDate.toData(),
        createdAt = createdAt
    )

internal fun AssetEntity.toLocal() =
    AssetLocal(
        id = id,
        name = name,
        dayCount = dayCount,
        buyDate = buyDate.toLocal(),
        createdAt = createdAt
    )