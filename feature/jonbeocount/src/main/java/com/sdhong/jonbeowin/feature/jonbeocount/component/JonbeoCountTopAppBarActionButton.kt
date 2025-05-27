package com.sdhong.jonbeowin.feature.jonbeocount.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sdhong.jonbeowin.feature.jonbeocount.R

@Composable
internal fun JonbeoCountTopAppBarActionButton(
    isEditMode: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.minimumInteractiveComponentSize()
            .size(40.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = null,
                onClick = onClick,
                indication = ripple()
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(
                if (isEditMode) R.string.remove
                else R.string.edit
            ),
            color = colorResource(R.color.blue),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun JonbeoCountTopAppBarActionButtonPreview() {
    JonbeoCountTopAppBarActionButton(
        isEditMode = false,
        onClick = {}
    )
}