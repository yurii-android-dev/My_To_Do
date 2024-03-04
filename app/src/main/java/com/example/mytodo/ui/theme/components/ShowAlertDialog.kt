package com.example.mytodo.ui.theme.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.mytodo.R

@Composable
fun ShowAlertDialog(
    title: Int,
    description: Int,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit
) {

    AlertDialog(
        title = {
            Text(text = stringResource(title))
        },
        text = {
            Text(text = stringResource(description))
        },
        dismissButton = {
            Button(onClick = onDismissClick) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            Button(onClick = onConfirmClick) {
                Text(text = stringResource(R.string.yes))
            }
        },
        onDismissRequest = onDismissRequest
    )

}