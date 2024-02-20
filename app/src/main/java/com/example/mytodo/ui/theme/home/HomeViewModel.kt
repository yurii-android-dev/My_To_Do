package com.example.mytodo.ui.theme.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

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

}