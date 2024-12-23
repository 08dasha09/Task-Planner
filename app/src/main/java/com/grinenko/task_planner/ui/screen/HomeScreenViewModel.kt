package com.grinenko.task_planner.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grinenko.task_planner.data.Tasks
import com.grinenko.task_planner.data.TasksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(private val tasksRepository: TasksRepository): ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val homeUiState: StateFlow<HomeUiState> = tasksRepository.getAll().map {
        HomeUiState(tasksList = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = HomeUiState()
    )

    suspend fun addTasks(task: Tasks){
             tasksRepository.insert(task)
    }

    suspend fun deleteTasks(task: Tasks){
        tasksRepository.delete(task)
    }



}

data class HomeUiState(
    val tasksList: List<Tasks> = listOf()
)