package com.example.mytodo.ui.theme.addtodo

import androidx.lifecycle.ViewModel
import com.example.mytodo.models.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddTodoAndUpdateViewModel : ViewModel() {

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

}