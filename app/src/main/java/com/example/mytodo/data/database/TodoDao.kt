package com.example.mytodo.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mytodo.models.Priority
import com.example.mytodo.models.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_entity")
    fun getTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todo_entity ORDER BY priority = :priority")
    fun getTodosWithPriority(priority: Priority): Flow<List<Todo>>

    @Query("DELETE FROM todo_entity")
    suspend fun deleteAllTodos()

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

}