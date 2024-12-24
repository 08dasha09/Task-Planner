package com.grinenko.task_planner.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grinenko.task_planner.R
import com.grinenko.task_planner.data.Tasks
import com.grinenko.task_planner.ui.AppViewModelProvider
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Tasks,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var isChecked by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

    var taskName by remember { mutableStateOf(task.taskName) }
    var description by remember { mutableStateOf(task.description) }
    var dueDate by remember { mutableStateOf(task.date) }
    var priority by remember { mutableIntStateOf(task.priority) }

    val coroutineScope = rememberCoroutineScope()

    // Card layout
    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 4.dp, end = 16.dp)
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ){
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp)
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = !isChecked }
                )

                Text(
                    text = task.taskName,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 4.dp, end = 16.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .clickable { isExpanded = !isExpanded },
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (isChecked) TextDecoration.LineThrough else null,
                    color = if (isChecked) Color.Red else Color.Black
                )

                // Delete task button
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.deleteTasks(task)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(painter = painterResource(id = R.drawable.delete_24), contentDescription = "Delete")
                }

                // Edit task button
                IconButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(painter = painterResource(id = R.drawable._09528), contentDescription = "Edit")
                }
            }

            if (isExpanded) {
                Text(
                    text = "Name: ${task.taskName}",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 4.dp, end = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .weight(1f),
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (isChecked) TextDecoration.LineThrough else null,
                    color = if (isChecked) Color.Red else Color.Black
                )
                Text(
                    text = "Description: ${task.description}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "Date: ${task.date}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "Priority: ${task.priority}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

    // Modal Bottom Sheet for editing task
    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = taskName,
                    onValueChange = { taskName = it },
                    label = { Text("Task Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Due Date") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = priority.toString(),
                    onValueChange = { priority = it.toIntOrNull() ?: 1 },
                    label = { Text("Priority") },
                    modifier = Modifier.fillMaxWidth()
                )

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateTask(
                                task.copy(
                                    taskName = taskName,
                                    description = description,
                                    date = dueDate,
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
    }
}



@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskItem(Tasks(1,"Develop Project", "Description", "12/12/2024", 1))
}