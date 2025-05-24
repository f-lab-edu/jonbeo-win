package com.sdhong.jonbeowin.feature.asset.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sdhong.jonbeowin.feature.asset.R
import com.sdhong.jonbeowin.feature.asset.model.BuyDateModel
import com.sdhong.jonbeowin.feature.asset.uistate.AssetUiState

@Composable
internal fun AssetContent(
    uiState: AssetUiState,
    @StringRes buttonTextId: Int,
    onAssetNameChange: (String) -> Unit,
    onClickBuyDate: () -> Unit,
    onClickConfirm: (String) -> Unit
) {
    when (uiState) {
        AssetUiState.Idle -> Unit

        is AssetUiState.Success -> {
            val name = uiState.asset.name
            val buyDate = uiState.asset.buyDate

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AssetCard(
                    assetName = name,
                    onAssetNameChange = onAssetNameChange,
                    buyDateText = if (buyDate == BuyDateModel.Default) {
                        stringResource(R.string.choose_date)
                    } else {
                        stringResource(
                            R.string.date_format,
                            buyDate.year,
                            buyDate.month,
                            buyDate.day
                        )
                    },
                    onClickBuyDate = onClickBuyDate
                )
                AssetConfirmButton(
                    buttonTextId = buttonTextId,
                    onClickConfirm = { onClickConfirm(uiState.asset.name) }
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
        uiState = AssetUiState.Error,
        buttonTextId = R.string.save,
        onAssetNameChange = {},
        onClickBuyDate = {},
        onClickConfirm = {}
    )
}