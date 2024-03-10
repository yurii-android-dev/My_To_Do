package com.example.mytodo.ui.theme.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mytodo.MyTodoApplication
import com.example.mytodo.data.repository.TodoRepository
import com.example.mytodo.models.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTodos()
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update { state ->
            state.copy(searchText = text)
        }
    }

    fun updateIsSearchExpanded() {
        _uiState.update { state ->
            state.copy(isSearchExpanded = !state.isSearchExpanded)
        }
    }

    fun updateIsSortExpanded() {
        _uiState.update { state ->
            state.copy(isSortExpanded = !state.isSortExpanded)
        }
    }

    fun updateIsMoreExpanded() {
        _uiState.update { state ->
            state.copy(isMoreExpanded = !state.isMoreExpanded)
        }
    }

    fun updateIsAlertDialogOpen() {
        _uiState.update { state ->
            state.copy(isAlertDialogOpen = !state.isAlertDialogOpen)
        }
    }

    fun getTodos(priority: Priority = Priority.NONE) {
        try {
            viewModelScope.launch {
                repository.getTodosWithPriority(priority).collect { todos ->
                    _uiState.update { state ->
                        state.copy(todos = todos)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("HomeViewModel", e.message.toString())
        }
    }

    fun searchTodos(text: String) {
        try {
            viewModelScope.launch {
                repository.getTodosBySearch(text).collect { searchTodos ->
                    _uiState.update { state ->
                        state.copy(searchTodos = searchTodos)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("HomeViewModel", e.message.toString())
        }
    }

    fun clearSearchTodos() {
        _uiState.update { state ->
            state.copy(searchTodos = emptyList())
        }
    }

    fun deleteTodos() {
        viewModelScope.launch {
            repository.deleteAllTodos()
        }
    }

    companion object {
        val FACTORY = viewModelFactory {
            initializer {
                val repository = (this[APPLICATION_KEY] as MyTodoApplication).container
                    .todoRepository
                HomeViewModel(repository)
            }
        }
    }

}