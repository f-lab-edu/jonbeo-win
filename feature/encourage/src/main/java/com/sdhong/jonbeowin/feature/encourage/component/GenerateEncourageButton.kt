package com.sdhong.jonbeowin.feature.encourage.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.sdhong.jonbeowin.feature.encourage.R

@Composable
internal fun GenerateEncourageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.padding(vertical = 3.dp),
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(8.dp),
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                contentDescription = null
            )
            Spacer(Modifier.size(2.dp))
            Text(
                text = stringResource(R.string.generate)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenerateEncourageButtonPreview() {
    GenerateEncourageButton(onClick = {})
}