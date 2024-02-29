package com.example.mytodo.ui.theme.addtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mytodo.MyTodoApplication
import com.example.mytodo.data.repository.TodoRepository
import com.example.mytodo.models.Priority
import com.example.mytodo.models.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTodoAndUpdateViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTodoAndUpdateUiState())
    val uiState = _uiState.asStateFlow()

    fun onTitleTextChanged(text: String) {
        _uiState.update { state ->
            state.copy(titleText = text)
        }
    }

    fun onDescriptionTextChanged(text: String) {
        _uiState.update { state ->
            state.copy(descriptionText = text)
        }
    }

    fun updateDropDownMenuExpanded(isExpanded: Boolean) {
        _uiState.update { state ->
            state.copy(isDropDownMenuExpanded = isExpanded)
        }
    }

    fun toogleDropDownMenuExpanded() {
        _uiState.update { state ->
            state.copy(isDropDownMenuExpanded = !state.isDropDownMenuExpanded)
        }
    }

    fun updatePriority(priority: Priority) {
        _uiState.update { state ->
            state.copy(priority = priority)
        }
    }

    fun addTodo() {
        viewModelScope.launch {
            val todo = Todo(
                title = _uiState.value.titleText,
                priority = _uiState.value.priority,
                description = _uiState.value.descriptionText
            )
            repository.insertTodo(todo)
        }
    }

    companion object {
        val FACTORY = viewModelFactory {
            initializer {
                val repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyTodoApplication).container
                    .todoRepository
                AddTodoAndUpdateViewModel(repository)
            }
        }
    }

}