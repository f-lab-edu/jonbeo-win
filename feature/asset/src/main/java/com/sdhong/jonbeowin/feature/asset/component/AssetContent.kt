package com.sdhong.jonbeowin.feature.asset.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sdhong.jonbeowin.feature.asset.R
import com.sdhong.jonbeowin.feature.asset.uistate.AssetUiState

@Composable
internal fun AssetContent(
    uiState: AssetUiState,
    @StringRes buttonTextId: Int,
    onClickBuyDate: () -> Unit,
    onClickConfirm: (String) -> Unit
) {
    var assetName by rememberSaveable { mutableStateOf("") }

    when (uiState) {
        AssetUiState.Idle -> Unit

        is AssetUiState.AssetDetailInitial -> {
            assetName = uiState.initialAsset.name
            val buyDate = uiState.initialAsset.buyDate

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AssetCard(
                    assetName = assetName,
                    onAssetNameChange = { assetName = it },
                    buyDateText = stringResource(
                        R.string.date_format,
                        buyDate.year,
                        buyDate.month,
                        buyDate.day
                    ),
                    onClickBuyDate = onClickBuyDate
                )
                AssetConfirmButton(
                    buttonTextId = buttonTextId,
                    onClickConfirm = { onClickConfirm(assetName) }
                )
            }
        }

        AssetUiState.AddAssetInitial -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AssetCard(
                    assetName = assetName,
                    onAssetNameChange = { assetName = it },
                    buyDateText = stringResource(R.string.choose_date),
                    onClickBuyDate = onClickBuyDate
                )
                AssetConfirmButton(
                    buttonTextId = buttonTextId,
                    onClickConfirm = { onClickConfirm(assetName) }
                )
            }
        }

        is AssetUiState.AssetDateSelected -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AssetCard(
                    assetName = assetName,
                    onAssetNameChange = { assetName = it },
                    buyDateText = stringResource(
                        R.string.date_format,
                        uiState.buyDate.year,
                        uiState.buyDate.month,
                        uiState.buyDate.day
                    ),
                    onClickBuyDate = onClickBuyDate
                )
                AssetConfirmButton(
                    buttonTextId = buttonTextId,
                    onClickConfirm = { onClickConfirm(assetName) }
                )
            }
        }

        AssetUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.asset_detail_error_message),
                    color = colorResource(R.color.red)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetContentPreview() {
    AssetContent(
        uiState = AssetUiState.AddAssetInitial,
        buttonTextId = R.string.save,
        onClickBuyDate = {},
        onClickConfirm = {}
    )
}