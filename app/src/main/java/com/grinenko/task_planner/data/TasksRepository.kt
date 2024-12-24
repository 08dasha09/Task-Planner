package com.grinenko.task_planner.data

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun insert(task: Tasks)
    suspend fun delete(task: Tasks)
    suspend fun update(task: Tasks)
    fun getAll():Flow<List<Tasks>>
}