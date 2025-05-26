package com.sdhong.jonbeowin.feature.asset.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sdhong.jonbeowin.feature.asset.R

@Composable
internal fun AssetConfirmButton(
    @StringRes buttonTextId: Int,
    onClickConfirm: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClickConfirm,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue)
        )
    ) {
        Text(
            text = stringResource(buttonTextId),
            color = colorResource(R.color.white)
        )
    }
}

@Preview
@Composable
private fun AssetConfirmButtonPreview() {
    AssetConfirmButton(
        buttonTextId = R.string.fix,
        onClickConfirm = {}
    )
}