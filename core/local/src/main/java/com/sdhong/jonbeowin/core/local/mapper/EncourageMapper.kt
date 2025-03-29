package com.sdhong.jonbeowin.core.local.mapper

import com.sdhong.jonbeowin.core.data.model.EncourageEntity
import com.sdhong.jonbeowin.core.local.model.EncourageLocal

internal fun EncourageLocal.toData() =
    EncourageEntity(
        id = id,
        content = content,
        createdAt = createdAt
    )

internal fun EncourageEntity.toLocal() =
    EncourageLocal(
        id = id,
        content = content,
        createdAt = createdAt
    )