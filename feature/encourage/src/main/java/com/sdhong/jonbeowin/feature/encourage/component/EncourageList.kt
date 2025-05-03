package com.sdhong.jonbeowin.feature.encourage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageUiState

@Composable
internal fun EncourageList(
    modifier: Modifier = Modifier,
    uiState: EncourageUiState,
    onEncourageItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (uiState) {
            is EncourageUiState.Success -> {
                itemsIndexed(uiState.encourageItemList) { index, item ->
                    EncourageCard(
                        modifier = Modifier.defaultMinSize(minHeight = 56.dp),
                        enabled = uiState.isEditMode,
                        onClick = {
                            onEncourageItemClick(index)
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = item.content
                            )
                            if (uiState.isEditMode) {
                                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                                    Checkbox(
                                        checked = uiState.encourageItemList[index].isChecked,
                                        onCheckedChange = null,
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(0xFF2563EB)
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            else -> Unit
        }
    }
}