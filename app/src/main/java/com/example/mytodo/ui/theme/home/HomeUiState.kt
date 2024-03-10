package com.example.mytodo.ui.theme.home

import com.example.mytodo.models.Todo

data class HomeUiState(
    val todos: List<Todo> = emptyList(),
    val searchTodos: List<Todo> = emptyList(),
    val searchText: String = "",
    val isSearchExpanded: Boolean = false,
    val isSortExpanded: Boolean = false,
    val isMoreExpanded: Boolean = false,
    val isAlertDialogOpen: Boolean = false
)
