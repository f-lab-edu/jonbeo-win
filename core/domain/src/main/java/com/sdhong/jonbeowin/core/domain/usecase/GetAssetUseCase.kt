package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.repository.AssetRepository
import javax.inject.Inject

class GetAssetUseCase @Inject constructor(
    private val assetRepository: AssetRepository
) {

    operator fun invoke(assetId: Int) = assetRepository.getAsset(assetId)
}