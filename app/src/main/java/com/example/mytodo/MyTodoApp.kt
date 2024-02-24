package com.example.mytodo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytodo.ui.theme.addtodo.AddTodoScreen
import com.example.mytodo.ui.theme.home.HomeScreen


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
                onTodoClicked = {},
                onSearchClick = {},
                onSortClick = {},
                onDeleteAllClick = {},
                addFloatingButtonClicked = {
                    navController.navigate(Screens.AddTodo.route)
                }
            )
        }
        composable(Screens.AddTodo.route) {
            AddTodoScreen(
                navigateBackClicked = {
                    navController.popBackStack()
                },
                addClicked = {}
            )
        }
    }
}