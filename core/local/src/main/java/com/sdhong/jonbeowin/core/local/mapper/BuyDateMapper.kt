package com.sdhong.jonbeowin.core.local.mapper

import com.sdhong.jonbeowin.core.data.model.BuyDateEntity
import com.sdhong.jonbeowin.core.local.model.BuyDateLocal

internal fun BuyDateLocal.toData() =
    BuyDateEntity(
        year = year,
        month = month,
        day = day
    )

internal fun BuyDateEntity.toLocal() =
    BuyDateLocal(
        year = year,
        month = month,
        day = day
    )