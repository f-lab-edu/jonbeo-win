package com.sdhong.jonbeowin.feature.encourage.mapper

import com.sdhong.jonbeowin.core.domain.model.Encourage
import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel

internal fun EncourageModel.toDomain() =
    Encourage(
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