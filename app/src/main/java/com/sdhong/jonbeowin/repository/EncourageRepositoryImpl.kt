package com.sdhong.jonbeowin.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.sdhong.jonbeowin.local.dao.EncourageDao
import com.sdhong.jonbeowin.local.model.Encourage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EncourageRepositoryImpl @Inject constructor(
    private val encourageDao: EncourageDao,
    private val generativeModel: GenerativeModel
) : EncourageRepository {

    override fun flowAllEncourages(): Flow<List<Encourage>> = encourageDao.getAll()

    override suspend fun update(encourage: Encourage) {
        encourageDao.update(encourage)
    }

    override suspend fun delete(encourageIds: Set<Int>) {
        encourageDao.delete(encourageIds)
    }

    override suspend fun generateContent(): String? {
        return generativeModel.generateContent(
            prompt = "주식이나 코인을 매수하지 말고 버틸 수 있도록 힘낼 수 있는 30자 이내인 문장 한 마디만 출력해줘."
        ).text
    }
}