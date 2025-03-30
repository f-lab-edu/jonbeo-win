package com.sdhong.jonbeowin.feature.jonbeocount.mapper

import com.sdhong.jonbeowin.core.domain.model.Encourage
import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountModel

internal fun JonbeoCountModel.toDomain() =
    JonbeoCount(
        id = id,
        content = content,
        createdAt = createdAt
    )

internal fun Encourage.toPresentation() =
    EncourageModel(
        id = id,
        content = content,
        createdAt = createdAt,
        isEditMode = false,
        isChecked = false
    )