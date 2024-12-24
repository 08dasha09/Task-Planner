package com.grinenko.task_planner.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Insert
    suspend fun insert(task: Tasks)
    @Delete
    suspend fun delete(task: Tasks)

    @Update
    suspend fun update(task: Tasks)

    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<Tasks>>
}