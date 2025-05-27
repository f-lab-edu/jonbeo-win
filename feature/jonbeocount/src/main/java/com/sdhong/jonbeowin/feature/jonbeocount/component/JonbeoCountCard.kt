package com.sdhong.jonbeowin.feature.jonbeocount.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sdhong.jonbeowin.feature.jonbeocount.R

@Composable
internal fun JonbeoCountCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.shape,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.defaultMinSize(minHeight = 56.dp),
        enabled = enabled,
        shape = shape,
        color = colorResource(R.color.white),
        border = BorderStroke(1.dp, colorResource(R.color.light_gray)),
        onClick = onClick,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun JonbeoCountCardPreview() {
    JonbeoCountCard(
        enabled = true,
        onClick = {}
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Jonbeo Count Card",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.jonbeo_day_count, 5)
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
                tint = colorResource(R.color.dusk_gray),
                contentDescription = null
            )
        }
    }
}