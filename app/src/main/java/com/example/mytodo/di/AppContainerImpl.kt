package com.example.mytodo.di

import android.content.Context
import com.example.mytodo.data.database.TodoDatabase
import com.example.mytodo.data.repository.TodoRepository
import com.example.mytodo.data.repository.TodoRepositoryImpl

class AppContainerImpl(context: Context) : AppContainer {

    override val todoRepository: TodoRepository by lazy {
        TodoRepositoryImpl(TodoDatabase.getDatabase(context).todoDao())
    }

}