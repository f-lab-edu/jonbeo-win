package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.repository.EncourageRepository

class DeleteEncourageUseCase @Inject constructor(
    private val encourageRepository: EncourageRepository
) {
    suspend operator fun invoke(encourageIds: Set<Int>) {
        encourageRepository.delete(encourageIds)
    }
}