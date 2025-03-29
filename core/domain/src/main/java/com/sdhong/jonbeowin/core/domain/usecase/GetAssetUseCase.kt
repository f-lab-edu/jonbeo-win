package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.repository.JonbeoRepository
import javax.inject.Inject

class GetAssetUseCase @Inject constructor(
    private val jonbeoRepository: JonbeoRepository
) {

    operator fun invoke(assetId: Int) = jonbeoRepository.getAsset(assetId)
}