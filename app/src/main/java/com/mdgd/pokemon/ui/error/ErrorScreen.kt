package com.mdgd.pokemon.ui.error

import android.content.res.Configuration
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ErrorScreen(
    trigger: MutableState<Boolean>, title: MutableState<String>, message: MutableState<String>
) {
    if (trigger.value) {
        MaterialTheme {
            AlertDialog(
                title = {
                    Text(text = title.value)
                },
                text = {
                    Text(text = message.value)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            trigger.value = false
                        },
                    ) {
                        Text(text = stringResource(id = android.R.string.ok))
                    }
                },
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    trigger.value = false
                },
            )
        }
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun ErrorPreviewThemeLight() {
    MaterialTheme {
        ErrorScreen(mutableStateOf(true), mutableStateOf("Title"), mutableStateOf("Message"))
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun ErrorPreviewThemeDark() {
    MaterialTheme {
        ErrorScreen(
            mutableStateOf(true), mutableStateOf("Title"), mutableStateOf("Message")
        )
    }
}
