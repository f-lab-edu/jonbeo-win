package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.model.Asset
import com.sdhong.jonbeowin.core.domain.repository.JonbeoRepository
import javax.inject.Inject

class UpdateAssetUseCase @Inject constructor(
    private val jonbeoRepository: JonbeoRepository
) {
    suspend operator fun invoke(asset: Asset) {
        jonbeoRepository.updateAsset(asset)
    }
}