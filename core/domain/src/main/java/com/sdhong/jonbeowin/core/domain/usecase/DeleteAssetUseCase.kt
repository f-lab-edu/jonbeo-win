package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.repository.JonbeoRepository
import javax.inject.Inject

class DeleteAssetUseCase @Inject constructor(
    private val jonbeoRepository: JonbeoRepository
) {
    suspend operator fun invoke(encourageIds: Set<Int>) {
        jonbeoRepository.delete(encourageIds)
    }
}