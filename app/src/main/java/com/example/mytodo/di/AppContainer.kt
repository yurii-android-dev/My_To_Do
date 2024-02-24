package com.example.mytodo.di

import com.example.mytodo.data.repository.TodoRepository

interface AppContainer {

    val todoRepository: TodoRepository

}


