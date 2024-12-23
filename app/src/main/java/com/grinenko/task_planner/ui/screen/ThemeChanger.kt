package com.grinenko.task_planner.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeChange(
    onThemeChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        Text(
            "Pick a theme",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = {
                    onThemeChange(true)
                    onDismiss()
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("Dark Theme")
            }

            Button(
                onClick = {
                    onThemeChange(false)
                    onDismiss()
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("Light Theme")
            }
        }
    }
}



