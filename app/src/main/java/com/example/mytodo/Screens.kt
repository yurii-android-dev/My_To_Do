package com.example.mytodo

sealed class Screens(val route: String) {

    data object Home: Screens("home_screen")

}