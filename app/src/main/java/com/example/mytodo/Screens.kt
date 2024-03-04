package com.example.mytodo

const val UPDATE_ARGUMENT_KEY = "todoId"

sealed class Screens(val route: String) {

    data object Home: Screens("home_screen")
    data object AddTodo: Screens("add_todo_screen")
    data object UpdateTodo: Screens("update_todo_screen/{$UPDATE_ARGUMENT_KEY}") {
        fun passId(id: Int) = "update_todo_screen/$id"
    }
}