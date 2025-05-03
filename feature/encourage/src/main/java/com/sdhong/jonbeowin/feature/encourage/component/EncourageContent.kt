package com.sdhong.jonbeowin.feature.encourage.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.sdhong.jonbeowin.feature.encourage.R
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageUiState

@Composable
internal fun EncourageContent(
    uiState: EncourageUiState,
    onEncourageItemClick: (Int) -> Unit
) {
    when (uiState) {
        EncourageUiState.Idle -> Unit

        EncourageUiState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.encourage_list_empty_message),
                    color = Color(0xFF868686)
                )
            }
        }

        is EncourageUiState.Success -> {
            EncourageList(
                items = uiState.encourageItemList,
                isEditMode = uiState.isEditMode,
                onEncourageItemClick = onEncourageItemClick
            )
        }

        EncourageUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.encourage_list_error_message),
                    color = Color(0xFFEB4444)
                )
            }
        }
    }
}
