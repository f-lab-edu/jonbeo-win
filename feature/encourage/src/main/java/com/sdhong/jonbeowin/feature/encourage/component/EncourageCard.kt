package com.sdhong.jonbeowin.feature.encourage.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.sdhong.jonbeowin.feature.encourage.R

@Composable
internal fun EncourageCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.shape,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        color = colorResource(R.color.white),
        border = BorderStroke(1.dp, colorResource(R.color.black)),
        onClick = onClick,
        content = content
    )
}
