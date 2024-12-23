package com.grinenko.task_planner.data

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun insert(task: Tasks)
    suspend fun delete(task: Tasks)
    fun getAll():Flow<List<Tasks>>
}