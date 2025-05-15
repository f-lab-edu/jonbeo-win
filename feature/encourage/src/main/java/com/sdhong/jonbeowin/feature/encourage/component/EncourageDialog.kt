package com.sdhong.jonbeowin.feature.encourage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sdhong.jonbeowin.feature.encourage.R
import com.sdhong.jonbeowin.feature.encourage.uistate.EncourageDialogUiState

@Composable
internal fun EncourageDialog(
    uiState: EncourageDialogUiState,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit,
    onGenerateClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = onCloseClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(colorResource(R.color.white))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                EncourageDialogUiState.Loading -> {
                    CircularProgressIndicator(color = colorResource(R.color.blue))
                }

                is EncourageDialogUiState.Success -> {
                    Text(
                        text = uiState.content,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }

                EncourageDialogUiState.Error -> {
                    Text(
                        text = stringResource(R.string.encourage_dialog_error_message),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.red)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onCloseClick,
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.red))
                ) {
                    Text(text = stringResource(R.string.close))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onSaveClick,
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.blue)),
                    enabled = uiState !is EncourageDialogUiState.Loading
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onGenerateClick,
                colors = ButtonDefaults.buttonColors(colorResource(R.color.black)),
                enabled = uiState !is EncourageDialogUiState.Loading
            ) {
                Text(text = stringResource(R.string.regenerate))
            }
        }
    }
}

@Preview
@Composable
private fun EncourageDialogPreview() {
    EncourageDialog(
        uiState = EncourageDialogUiState.Error,
        onCloseClick = {},
        onSaveClick = {},
        onGenerateClick = {}
    )
}