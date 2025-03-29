package com.sdhong.jonbeowin.core.remote.impl

import com.google.ai.client.generativeai.GenerativeModel
import com.sdhong.jonbeowin.core.data.remote.EncourageRemoteDataSource
import javax.inject.Inject

class EncourageRemoteDataSourceImpl @Inject constructor(
    private val generativeApi: GenerativeModel
) : EncourageRemoteDataSource {

    override suspend fun generateContent(): String? =
        generativeApi.generateContent(
            prompt = "주식이나 코인을 매수하지 말고 버틸 수 있도록 힘낼 수 있는 30자 이내인 문장 한 마디만 출력해줘."
        ).text
}