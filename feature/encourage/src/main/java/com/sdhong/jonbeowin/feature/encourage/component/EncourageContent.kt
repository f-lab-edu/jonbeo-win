package com.sdhong.jonbeowin.feature.encourage.component

import androidx.compose.foundation.layout.Box
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
import com.sdhong.jonbeowin.feature.encourage.R
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageUiState

@Composable
internal fun EncourageContent(
    uiState: EncourageUiState,
    onEncourageItemClick: (Int) -> Unit,
    onGenerateButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            EncourageUiState.Idle -> Unit

            EncourageUiState.Empty -> {
                Text(
                    text = stringResource(R.string.encourage_list_empty_message),
                    color = colorResource(R.color.dusk_gray)
                )
            }

            is EncourageUiState.Success -> {
                EncourageList(
                    items = uiState.encourageItemList,
                    isEditMode = uiState.isEditMode,
                    onEncourageItemClick = onEncourageItemClick
                )
            }

            EncourageUiState.Error -> {
                Text(
                    text = stringResource(R.string.encourage_list_error_message),
                    color = colorResource(R.color.red)
                )
            }
        }

        GenerateEncourageButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            onClick = onGenerateButtonClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EncourageContentPreview() {
    EncourageContent(
        uiState = EncourageUiState.Error,
        onEncourageItemClick = {},
        onGenerateButtonClick = {}
    )
}