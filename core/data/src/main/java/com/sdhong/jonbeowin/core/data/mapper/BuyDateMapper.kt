package com.sdhong.jonbeowin.core.data.mapper

import com.sdhong.jonbeowin.core.data.model.BuyDateEntity
import com.sdhong.jonbeowin.core.domain.model.BuyDate

internal fun BuyDateEntity.toDomain() =
    BuyDate(
        year = year,
        month = month,
        day = day
    )

internal fun BuyDate.toData() =
    BuyDateEntity(
        year = year,
        month = month,
        day = day
    )