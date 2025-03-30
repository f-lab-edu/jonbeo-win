package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.repository.AssetRepository
import javax.inject.Inject

class GetAssetListUseCase @Inject constructor(
    private val assetRepository: AssetRepository
) {

    operator fun invoke() = assetRepository.getAllAssets()
}