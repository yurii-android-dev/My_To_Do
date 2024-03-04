package com.example.mytodo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mytodo.ui.theme.addtodo.AddTodoScreen
import com.example.mytodo.ui.theme.home.HomeScreen
import com.example.mytodo.ui.theme.update.UpdateScreen


@Composable
fun MyTodoApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
    ) {
        composable(Screens.Home.route) {
            HomeScreen(
                navController = navController
            )
        }
        composable(Screens.AddTodo.route) {
            AddTodoScreen(
                navController = navController
            )
        }
        composable(
            route = Screens.UpdateTodo.route,
            arguments = listOf(navArgument(UPDATE_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            UpdateScreen(
                navController = navController
            )
        }
    }
}