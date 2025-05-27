package com.sdhong.jonbeowin.feature.jonbeocount.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdhong.jonbeowin.feature.jonbeocount.R
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.JonbeoCountUiState

@Composable
internal fun JonbeoCountContent(
    uiState: JonbeoCountUiState,
    onJonbeoCountItemClick: (Int) -> Unit,
    onAddAssetButtonClick: () -> Unit,
    toggleEditMode: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.light_blue))
                .height(64.dp)
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.title_jonbeo_count), fontSize = 22.sp)
            if (uiState is JonbeoCountUiState.Success) {
                JonbeoCountTopAppBarActionButton(
                    isEditMode = uiState.isEditMode,
                    onClick = toggleEditMode
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                JonbeoCountUiState.Idle -> Unit

                JonbeoCountUiState.Empty -> {
                    Text(
                        text = stringResource(R.string.jonbeo_asset_empty_message),
                        color = colorResource(R.color.dusk_gray)
                    )
                }

                is JonbeoCountUiState.Success -> {
                    JonbeoCountList(
                        items = uiState.jonbeoCountItemList,
                        isEditMode = uiState.isEditMode,
                        onJonbeoCountItemClick = onJonbeoCountItemClick
                    )
                }

                JonbeoCountUiState.Error -> {
                    Text(
                        text = stringResource(R.string.jonbeo_asset_error_message),
                        color = colorResource(R.color.red)
                    )
                }
            }

            AddAssetButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp),
                onClick = onAddAssetButtonClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JonbeoCountContentPreview() {
    JonbeoCountContent(
        uiState = JonbeoCountUiState.Empty,
        onJonbeoCountItemClick = {},
        onAddAssetButtonClick = {},
        toggleEditMode = {}
    )
}