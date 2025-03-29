package com.sdhong.jonbeowin.core.domain.usecase

import com.sdhong.jonbeowin.core.domain.repository.EncourageRepository
import javax.inject.Inject

class GetEncourageListUseCase @Inject constructor(
    private val encourageRepository: EncourageRepository
) {

    operator fun invoke() = encourageRepository.getAllEncourages()
}