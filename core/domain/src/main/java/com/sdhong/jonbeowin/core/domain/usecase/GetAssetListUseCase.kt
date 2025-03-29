package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.repository.JonbeoRepository
import javax.inject.Inject

class GetAssetListUseCase @Inject constructor(
    private val jonbeoRepository: JonbeoRepository
) {

    operator fun invoke() = jonbeoRepository.getAllAssets()
}