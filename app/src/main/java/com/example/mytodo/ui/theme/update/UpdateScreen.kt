package com.example.mytodo.ui.theme.update

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import com.example.mytodo.ui.theme.MyToDoTheme
import com.example.mytodo.ui.theme.addtodo.AddTodoAndUpdateViewModel
import com.example.mytodo.ui.theme.components.InputContentSection

@Composable
fun UpdateScreen(
    viewModel: AddTodoAndUpdateViewModel =
        viewModel(factory = AddTodoAndUpdateViewModel.FACTORY)
) {

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            UpdateTodoTopBar(
                title = uiState.titleText,
                onCloseClicked = {},
                onDeleteClicked = {},
                onUpdateClicked = {}
            )
        }
    ) {  innerPadding ->
        InputContentSection(
            titleText = uiState.titleText,
            titleTextChanged = viewModel::onTitleTextChanged,
            isExpanded = uiState.isDropDownMenuExpanded,
            onExpandedChange = viewModel::updateDropDownMenuExpanded,
            priority = uiState.priority,
            onDismissRequest = { viewModel.toogleDropDownMenuExpanded() },
            onDropdownMenuItemClicked = { priority ->
                viewModel.updatePriority(priority)
                viewModel.toogleDropDownMenuExpanded()
            },
            descriptionText = uiState.descriptionText,
            descriptionTextChanged = viewModel::onDescriptionTextChanged,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTodoTopBar(
    title: String,
    onCloseClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onUpdateClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.padding(start = 24.dp)
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.close_icon),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        onCloseClicked()
                    }
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete_icon),
                modifier = Modifier.clickable {
                    onDeleteClicked()
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(id = R.string.check_icon),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                        onUpdateClicked()
                    }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun UpdateScreenPreview() {
    MyToDoTheme {
        UpdateScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateTodoAppBarPreview() {
    MyToDoTheme {
        UpdateTodoTopBar(
            title = "Example",
            onCloseClicked = {},
            onDeleteClicked = {},
            onUpdateClicked = {}
        )
    }
}