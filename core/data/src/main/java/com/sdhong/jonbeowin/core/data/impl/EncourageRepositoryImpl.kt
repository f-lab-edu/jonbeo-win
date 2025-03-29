package com.sdhong.jonbeowin.core.data.impl

import com.sdhong.jonbeowin.core.data.local.EncourageLocalDataSource
import com.sdhong.jonbeowin.core.data.mapper.toData
import com.sdhong.jonbeowin.core.data.mapper.toDomain
import com.sdhong.jonbeowin.core.data.remote.EncourageRemoteDataSource
import com.sdhong.jonbeowin.core.domain.model.Encourage
import com.sdhong.jonbeowin.core.domain.repository.EncourageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EncourageRepositoryImpl @Inject constructor(
    private val encourageLocalDataSource: EncourageLocalDataSource,
    private val encourageRemoteDataSource: EncourageRemoteDataSource
) : EncourageRepository {

    override fun getAllEncourages(): Flow<List<Encourage>> = flow {
        emit(encourageLocalDataSource.getAllEncourages())
    }.map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun update(encourage: Encourage) = encourageLocalDataSource.update(encourage.toData())

    override suspend fun delete(encourageIds: Set<Int>) = encourageLocalDataSource.delete(encourageIds)

    override suspend fun generateContent(): String? = encourageRemoteDataSource.generateContent()
}