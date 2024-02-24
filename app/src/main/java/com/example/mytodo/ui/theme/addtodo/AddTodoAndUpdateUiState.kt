package com.example.mytodo.ui.theme.addtodo

import com.example.mytodo.models.Priority

data class AddTodoAndUpdateUiState(
    val titleText: String = "",
    val isDropDownMenuExpanded: Boolean = false,
    val descriptionText: String = "",
    val priority: Priority = Priority.LOW,
    val userMessage: String = ""
)