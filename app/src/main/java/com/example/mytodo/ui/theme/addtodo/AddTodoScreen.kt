package com.example.mytodo.ui.theme.addtodo

import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mytodo.R
import com.example.mytodo.models.Priority
import com.example.mytodo.ui.theme.MyToDoTheme
import com.example.mytodo.ui.theme.components.InputContentSection

@Composable
fun AddTodoScreen(
    viewModel: AddTodoAndUpdateViewModel =
        viewModel(factory = AddTodoAndUpdateViewModel.FACTORY),
    navController: NavHostController
) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AddTodoTopBar(
                navigateBackClicked = {
                    navController.popBackStack()
                },
                addClicked = {
                    if (viewModel.titleText.value.isNotEmpty()) {
                        viewModel.addTodo()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(
                            context,
                            "Title can not be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    ) { paddingValues ->
        AddTodoBody(
            title = viewModel.titleText.value,
            isDropDownMenuExpanded = viewModel.isDropDownMenuExpanded.value,
            priority = viewModel.priority.value,
            description = viewModel.descriptionText.value,
            titleTextChanged = viewModel::onTitleTextChanged,
            onExpandedChange = viewModel::updateDropDownMenuExpanded,
            onDismissRequest = { viewModel.toogleDropDownMenuExpanded() },
            onDropdownMenuItemClicked = { priority ->
                viewModel.updatePriority(priority)
                viewModel.toogleDropDownMenuExpanded()
            },
            descriptionTextChanged = viewModel::onDescriptionTextChanged,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun AddTodoBody(
    title: String,
    isDropDownMenuExpanded: Boolean,
    priority: Priority,
    description: String,
    titleTextChanged: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    onDropdownMenuItemClicked: (Priority) -> Unit,
    descriptionTextChanged: (String) -> Unit,
    paddingValues: PaddingValues = PaddingValues.Absolute()
) {
    InputContentSection(
        titleText = title,
        titleTextChanged = titleTextChanged,
        isExpanded = isDropDownMenuExpanded,
        onExpandedChange = onExpandedChange,
        priority = priority,
        onDismissRequest = onDismissRequest,
        onDropdownMenuItemClicked = onDropdownMenuItemClicked,
        descriptionText = description,
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
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTodoBodyPreview() {
    MyToDoTheme {
        AddTodoBody(
            title = "Test Title",
            isDropDownMenuExpanded = false,
            priority = Priority.LOW,
            description = "Test Description",
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