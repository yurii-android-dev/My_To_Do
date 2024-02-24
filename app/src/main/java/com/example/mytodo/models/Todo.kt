package com.example.mytodo.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_entity")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("priority") val priority: Priority,
    @ColumnInfo("description") val description: String?
)
