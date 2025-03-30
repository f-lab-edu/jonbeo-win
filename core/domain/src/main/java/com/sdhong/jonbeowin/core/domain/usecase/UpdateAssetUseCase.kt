package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.model.Asset
import com.sdhong.jonbeowin.core.domain.repository.AssetRepository
import javax.inject.Inject

class UpdateAssetUseCase @Inject constructor(
    private val assetRepository: AssetRepository
) {
    suspend operator fun invoke(asset: Asset) {
        assetRepository.updateAsset(asset)
    }
}