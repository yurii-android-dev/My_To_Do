package com.example.mytodo.data.repository

import com.example.mytodo.models.Priority
import com.example.mytodo.models.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getTodosWithPriority(priority: Priority): Flow<List<Todo>>

    fun getTodoById(id: Int): Flow<Todo>

    fun getTodosBySearch(text: String): Flow<List<Todo>>

    suspend fun deleteAllTodos()

    suspend fun insertTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

}