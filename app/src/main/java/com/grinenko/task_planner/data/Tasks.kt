    package com.grinenko.task_planner.data

    import androidx.room.Entity
    import androidx.room.PrimaryKey

    @Entity(tableName = "tasks")
    data class Tasks(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val taskName: String,
        val description: String,
        val date: String,
        val priority: Int // 0 - low, 1 - medium, 2 - height
    )
