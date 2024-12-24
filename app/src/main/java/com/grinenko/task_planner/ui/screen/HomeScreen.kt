package com.grinenko.task_planner.ui.screen

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.grinenko.task_planner.ui.AppViewModelProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grinenko.task_planner.R
import com.grinenko.task_planner.data.Tasks
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ToDoMain(
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onThemeClick: () -> Unit = {}
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val homeUiState by viewModel.homeUiState.collectAsState()

    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$year-${month + 1}-$dayOfMonth"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                { TopAppBarLayout(onThemeClick = onThemeClick) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                },
                modifier = Modifier.padding(12.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { padding ->

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(homeUiState.tasksList) {
                TaskItem(it)
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                modifier = Modifier.wrapContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    var description by remember { mutableStateOf("") }
                    var priority by remember { mutableIntStateOf(1) }

                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Task Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        maxLines = 1,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.check_box),
                                contentDescription = "Task Name"
                            )
                        }
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )

                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = { },
                        label = { Text("Date") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { datePickerDialog.show() }) {
                                Icon(painter = painterResource(id = R.drawable._47310), contentDescription = "Pick Date")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = priority.toString(),
                        onValueChange = { priority = it.toIntOrNull() ?: 1 },
                        label = { Text("Priority (1-3)") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1
                    )

                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.addTasks(
                                    Tasks(
                                        taskName = text,
                                        description = description,
                                        date = selectedDate,
                                        priority = priority
                                    )
                                )
                                showBottomSheet = false
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.upload),
                            contentDescription = "Save Task"
                        )
                    }
                }
            }
            text = ""
        }
    }
}


@Composable
fun TopAppBarLayout(onThemeClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {

        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(top = 12.dp)
        ) {
            Icon(Icons.Filled.MoreVert, contentDescription = "More")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White),
            offset = DpOffset(x = 205.dp, y = 0.dp)
        ) {
            DropdownMenuItem(
                text = { Text("Change theme") },
                onClick = {
                    expanded = false
                    onThemeClick()
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.change_theme),
                        contentDescription = "Change theme"
                    )
                }
            )
        }

        Column(
            Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                "Your tasks",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(Date()),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ToDoMainPreview() {
    ToDoMain(onThemeClick = {})
}


