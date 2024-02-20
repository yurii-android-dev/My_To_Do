package com.example.mytodo.models

data class Todo(
    val id: Int,
    val title: String,
    val priority: Priority,
    val description: String?
)
