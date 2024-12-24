package com.grinenko.task_planner.data

import android.content.Context

class AppContainer(private val context: Context) {

    val tasksRepository: TasksRepository by lazy {
        TaskRepositoryImpl(TaskDatabase.getDatabase(context).tasksDao())
    }
}
