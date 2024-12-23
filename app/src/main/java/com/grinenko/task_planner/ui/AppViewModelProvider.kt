package com.grinenko.task_planner.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.initializer
import com.grinenko.task_planner.TaskApplication
import com.grinenko.task_planner.ui.screen.HomeScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            HomeScreenViewModel(taskApplication().container.tasksRepository)
        }
    }
}
fun CreationExtras.taskApplication(): TaskApplication = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskApplication)