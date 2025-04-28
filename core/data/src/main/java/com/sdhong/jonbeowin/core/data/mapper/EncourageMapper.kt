package com.sdhong.jonbeowin.core.data.mapper

import com.sdhong.jonbeowin.core.data.model.EncourageEntity
import com.sdhong.jonbeowin.core.domain.model.Encourage

internal fun EncourageEntity.toDomain() =
    Encourage(
        id = id,
        content = content,
        createdAt = createdAt
    )

internal fun Encourage.toData() =
    EncourageEntity(
        id = id,
        content = content,
        createdAt = createdAt
    )