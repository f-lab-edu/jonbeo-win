package com.sdhong.jonbeowin.feature.jonbeocount.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sdhong.jonbeowin.feature.jonbeocount.R
import com.sdhong.jonbeowin.feature.jonbeocount.model.BuyDateModel
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountModel

@Composable
internal fun JonbeoCountList(
    items: List<JonbeoCountModel>,
    isEditMode: Boolean,
    onJonbeoCountItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = items,
            key = { item -> item.id }
        ) { item ->
            JonbeoCountCard(
                onClick = {
                    onJonbeoCountItemClick(item.id)
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.jonbeo_day_count, item.dayCount)
                        )
                    }
                    if (isEditMode) {
                        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = null,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = colorResource(R.color.blue)
                                ),
                            )
                        }
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
                            tint = colorResource(R.color.dusk_gray),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JonbeoCountListPreview() {
    JonbeoCountList(
        items = listOf(
            JonbeoCountModel(
                id = 1,
                name = "Jonbeo Count Item 1",
                dayCount = 10,
                buyDate = BuyDateModel(2023, 10, 1),
                createdAt = "",
                isEditMode = true,
                isChecked = false
            ),
        ),
        isEditMode = true,
        onJonbeoCountItemClick = {}
    )
}