package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.model.Encourage
import com.sdhong.jonbeowin.core.domain.repository.EncourageRepository

class UpdateEncourageUseCase @Inject constructor(
    private val encourageRepository: EncourageRepository
) {
    suspend operator fun invoke(encourage: Encourage) {
        encourageRepository.update(encourage)
    }
}