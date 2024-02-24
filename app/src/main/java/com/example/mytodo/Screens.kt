package com.example.mytodo

sealed class Screens(val route: String) {

    data object Home: Screens("home_screen")
    data object AddTodo: Screens("add_todo_screen")

}