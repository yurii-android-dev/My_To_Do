package com.example.mytodo.data.repository

import com.example.mytodo.data.database.TodoDao
import com.example.mytodo.models.Priority
import com.example.mytodo.models.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val dao: TodoDao
): TodoRepository {

    override fun getTodosWithPriority(priority: Priority): Flow<List<Todo>> {
        return if (priority == Priority.NONE) dao.getTodos() else dao.getTodosWithPriority(priority)
    }

    override fun getTodosBySearch(text: String): Flow<List<Todo>> {
        return dao.getTodosBySearch(text)
    }

    override fun getTodoById(id: Int): Flow<Todo> {
        return dao.getTodoById(id)
    }

    override suspend fun deleteAllTodos() = dao.deleteAllTodos()

    override suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    override suspend fun updateTodo(todo: Todo) = dao.updateTodo(todo)

    override suspend fun deleteTodo(todo: Todo) = dao.deleteTodo(todo)
}