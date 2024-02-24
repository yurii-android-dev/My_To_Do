package com.example.mytodo.ui.theme.addtodo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytodo.R
import com.example.mytodo.models.Priority
import com.example.mytodo.ui.theme.MyToDoTheme
import com.example.mytodo.ui.theme.components.InputContentSection

@Composable
fun AddTodoScreen(
    addTodoViewModel: AddTodoAndUpdateViewModel = viewModel(),
    navigateBackClicked: () -> Unit,
    addClicked: () -> Unit
) {

    val uiState = addTodoViewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            AddTodoTopBar(
                navigateBackClicked = navigateBackClicked,
                addClicked = addClicked
            )
        }
    ) { paddingValues ->
        AddTodoBody(
            uiState = uiState,
            titleTextChanged = addTodoViewModel::onTitleTextChanged,
            onExpandedChange = addTodoViewModel::updateDropDownMenuExpanded,
            onDismissRequest = { addTodoViewModel.toogleDropDownMenuExpanded() },
            onDropdownMenuItemClicked = { priority ->
                addTodoViewModel.updatePriority(priority)
                addTodoViewModel.toogleDropDownMenuExpanded()
            },
            descriptionTextChanged = addTodoViewModel::onDescriptionTextChanged,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun AddTodoBody(
    uiState: AddTodoAndUpdateUiState,
    titleTextChanged: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    onDropdownMenuItemClicked: (Priority) -> Unit,
    descriptionTextChanged: (String) -> Unit,
    paddingValues: PaddingValues = PaddingValues.Absolute()
) {
    InputContentSection(
        titleText = uiState.titleText,
        titleTextChanged = titleTextChanged,
        isExpanded = uiState.isDropDownMenuExpanded,
        onExpandedChange = onExpandedChange,
        priority = uiState.priority,
        onDismissRequest = onDismissRequest,
        onDropdownMenuItemClicked = onDropdownMenuItemClicked,
        descriptionText = uiState.descriptionText,
        descriptionTextChanged = descriptionTextChanged,
        modifier = Modifier.padding(paddingValues)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoTopBar(
    navigateBackClicked: () -> Unit,
    addClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.add_task),
                modifier = Modifier.padding(start = 24.dp)
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.arrow_back_icon),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        navigateBackClicked()
                    }
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.check_icon),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                        addClicked()
                    }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary,
            navigationIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddTodoScreenPreview() {
    MyToDoTheme {
        AddTodoScreen(
            navigateBackClicked = {},
            addClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTodoBodyPreview() {
    MyToDoTheme {
        AddTodoBody(
            uiState = AddTodoAndUpdateUiState(),
            titleTextChanged = {},
            descriptionTextChanged = {},
            onExpandedChange = {},
            onDismissRequest = {},
            onDropdownMenuItemClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTodoTopBarPreview() {
    MyToDoTheme {
        AddTodoTopBar(
            navigateBackClicked = {},
            addClicked = {}
        )
    }
}