package com.sdhong.jonbeowin.feature.asset.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sdhong.jonbeowin.feature.asset.R

@Composable
internal fun AssetCard(
    assetName: String,
    onAssetNameChange: (String) -> Unit,
    buyDateText: String,
    onClickBuyDate: () -> Unit
) {
    Surface(
        modifier = Modifier.wrapContentHeight(),
        shape = CardDefaults.shape,
        color = colorResource(R.color.white),
        border = BorderStroke(1.dp, colorResource(R.color.light_gray))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(stringResource(R.string.asset_name))
            Spacer(Modifier.height(8.dp))
            BasicTextField(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                value = assetName,
                onValueChange = onAssetNameChange,
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = colorResource(R.color.dusk_gray),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(8.dp),
                    ) {
                        if (assetName.isEmpty()) {
                            Text(
                                text = stringResource(R.string.asset_name_hint),
                                style = MaterialTheme.typography.bodyLarge,
                                color = colorResource(R.color.light_gray)
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.buy_date))
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable(onClick = onClickBuyDate)
                    .border(
                        width = 2.dp,
                        color = colorResource(R.color.dusk_gray),
                        shape = RoundedCornerShape(4.dp)
                    ).padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buyDateText,
                    style = MaterialTheme.typography.bodyLarge
                )
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetCardPreview() {
    AssetCard(
        assetName = "삼성전자",
        onAssetNameChange = {},
        buyDateText = "2025/5/13",
        onClickBuyDate = {}
    )
}