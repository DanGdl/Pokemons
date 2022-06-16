package com.mdgd.pokemon.ui.error

import android.content.res.Configuration
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.composethemeadapter.MdcTheme

@Composable
fun ErrorScreen(params: MutableState<ErrorParams>) {
    if (params.value.isVisible) {
        AlertDialog(
            title = {
                Text(text = params.value.title)
            },
            text = {
                Text(text = params.value.message)
            },
            confirmButton = {
                Button(
                    onClick = {
                        params.value = params.value.hide()
                    },
                ) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            },
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                params.value = params.value.hide()
            }
        )
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun ErrorPreviewThemeLight() {
    MdcTheme {
        ErrorScreen(mutableStateOf(DefaultErrorParams()))
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun ErrorPreviewThemeDark() {
    MdcTheme {
        ErrorScreen(mutableStateOf(DefaultErrorParams(true)))
    }
}
