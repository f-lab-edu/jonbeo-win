package com.sdhong.jonbeowin.viewmodel

import androidx.lifecycle.ViewModel
import com.sdhong.jonbeowin.local.dao.AssetDao
import com.sdhong.jonbeowin.local.model.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class JonbeoCountViewModel @Inject constructor(
    private val assetDao: AssetDao
) : ViewModel() {

    private val _assetList = MutableStateFlow(
        listOf(
            Asset(
                id = 1,
                name = "삼성전자",
                dayCount = 127
            ),
            Asset(
                id = 2,
                name = "LG전자",
                dayCount = 127
            ),
            Asset(
                id = 3,
                name = "카카오",
                dayCount = 127
            ),
            Asset(
                id = 4,
                name = "네이버",
                dayCount = 127
            ),
            Asset(
                id = 5,
                name = "카카오뱅크",
                dayCount = 127
            ),
            Asset(
                id = 6,
                name = "토스뱅크",
                dayCount = 127
            ),
            Asset(
                id = 7,
                name = "신한은행",
                dayCount = 127
            ),
            Asset(
                id = 8,
                name = "우리은행",
                dayCount = 127
            ),
            Asset(
                id = 9,
                name = "농협은행",
                dayCount = 127
            ),
            Asset(
                id = 10,
                name = "국민은행",
                dayCount = 127
            )
        )
    )
    val assetList = _assetList.asStateFlow()

    fun editAssetList() {

    }
}