package com.grinenko.task_planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.grinenko.task_planner.ui.screen.ThemeChange
import com.grinenko.task_planner.ui.screen.ToDoMain
import com.grinenko.task_planner.ui.theme.TaskPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            var showThemePicker by remember { mutableStateOf(false) }

            TaskPlannerTheme(darkTheme = darkTheme) {
                ToDoMain(
                    onThemeClick = { showThemePicker = true }
                )

                if (showThemePicker) {
                    ThemeChange(
                        onThemeChange = { isDark ->
                            darkTheme = isDark
                            showThemePicker = false
                        },
                        onDismiss = { showThemePicker = false }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskPlannerTheme {
        ToDoMain()
    }
}
