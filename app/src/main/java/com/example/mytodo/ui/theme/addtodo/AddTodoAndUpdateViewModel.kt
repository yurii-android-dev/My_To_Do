package com.example.mytodo.ui.theme.addtodo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mytodo.MyTodoApplication
import com.example.mytodo.data.repository.TodoRepository
import com.example.mytodo.models.Priority
import com.example.mytodo.models.Todo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AddTodoAndUpdateViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: TodoRepository
) : ViewModel() {

    private val todoId: Int = savedStateHandle["todoId"] ?:-1

    private val todoById = repository.getTodoById(todoId)

    private var updateTodo: Todo? = null

    var updateTopBarText = mutableStateOf("")
        private set

    var isDeleteTodoAlertDialogOpen = mutableStateOf(false)
        private set

    var titleText = mutableStateOf(savedStateHandle["titleText"] ?: "")
        private set
    var isDropDownMenuExpanded = mutableStateOf(false)
        private set
    var descriptionText = mutableStateOf(savedStateHandle["descriptionText"] ?: "")
        private set
    var priority = mutableStateOf(savedStateHandle["priority"] ?: Priority.LOW)
        private set

    fun toogleIsDeleteTodoAlertDialogOpen() {
        isDeleteTodoAlertDialogOpen.value = !isDeleteTodoAlertDialogOpen.value
    }

    fun onTitleTextChanged(text: String) {
        titleText.value = text
        savedStateHandle["titleText"] = titleText.value
    }

    fun onDescriptionTextChanged(text: String) {
        descriptionText.value = text
        savedStateHandle["descriptionText"] = descriptionText.value
    }

    fun updateDropDownMenuExpanded(isExpanded: Boolean) {
        isDropDownMenuExpanded.value = isExpanded
    }

    fun toogleDropDownMenuExpanded() {
        isDropDownMenuExpanded.value = !isDropDownMenuExpanded.value
    }

    fun updatePriority(priorityType: Priority) {
        priority.value = priorityType
        savedStateHandle["priority"] = priority.value
    }

    fun addTodo() {
        viewModelScope.launch {
            val todo = Todo(
                title = titleText.value,
                priority = priority.value,
                description = descriptionText.value
            )
            repository.insertTodo(todo)
        }
    }

    fun getTodoWithId() {
        viewModelScope.launch {
            val job = this
            todoById.collect { todo ->
                updateTodo = todo
                updateTopBarText.value = todo.title
                titleText.value = todo.title
                priority.value = todo.priority
                descriptionText.value = todo.description ?: ""
                job.cancel()
            }
        }
    }

    fun updateTodo() {
        viewModelScope.launch {
            val todo = Todo(
                id = todoId,
                title = titleText.value,
                priority = priority.value,
                description = descriptionText.value
            )
            if (todo != updateTodo) {
                repository.updateTodo(todo)
            }
        }
    }

    fun deleteTodo() {
        viewModelScope.launch {
            updateTodo?.let { todo ->
                repository.deleteTodo(todo)
            }
        }
    }

    companion object {
        val FACTORY = viewModelFactory {
            initializer {
                val savedStateHandle = this.createSavedStateHandle()
                val repository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyTodoApplication).container
                    .todoRepository
                AddTodoAndUpdateViewModel(savedStateHandle, repository)
            }
        }
    }

}