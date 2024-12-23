package com.grinenko.task_planner.data

import  android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tasks::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object{
        @Volatile
        var instance: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return instance ?: synchronized(this){
                Room.databaseBuilder(context, TaskDatabase::class.java, "task_database")
                    .build().also {
                    instance = it
                }
            }
        }
    }
}