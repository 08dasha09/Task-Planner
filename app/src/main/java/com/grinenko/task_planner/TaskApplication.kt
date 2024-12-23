package com.grinenko.task_planner

import android.app.Application
import com.grinenko.task_planner.data.AppContainer

class TaskApplication: Application() {
    lateinit var  container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}