package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.repository.EncourageRepository

class GenerateEncourageUseCase @Inject constructor(
    private val encourageRepository: EncourageRepository
) {

    suspend operator fun invoke(): String? = encourageRepository.generateContent()?.trim()
}