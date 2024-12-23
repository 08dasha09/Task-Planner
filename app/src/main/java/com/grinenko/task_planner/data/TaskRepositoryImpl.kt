package com.grinenko.task_planner.data

import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val tasksDao: TasksDao): TasksRepository {

    override suspend fun insert(task: Tasks)  = tasksDao.insert(task)

    override suspend fun delete(task: Tasks)  = tasksDao.delete(task)

    override fun getAll(): Flow<List<Tasks>>  = tasksDao.getAll()
}