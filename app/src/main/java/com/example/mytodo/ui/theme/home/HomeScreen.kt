package com.example.mytodo.ui.theme.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mytodo.R
import com.example.mytodo.Screens
import com.example.mytodo.data.LocalSortPopupData.options
import com.example.mytodo.models.Priority
import com.example.mytodo.models.SortPopup
import com.example.mytodo.models.Todo
import com.example.mytodo.ui.theme.MyToDoTheme
import com.example.mytodo.ui.theme.components.ShowAlertDialog
import com.example.mytodo.util.toIconColor


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.FACTORY),
    navController: NavHostController
) {
    val uiState = homeViewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            HomeTopAppBar(
                viewModel = homeViewModel,
                uiState = uiState,
                onTextChange = homeViewModel::onSearchTextChanged,
                onSearchClick = {
                    homeViewModel.searchTodos(uiState.searchText)
                },
                onClearClick = {
                    homeViewModel.updateIsSearchExpanded()
                    homeViewModel.getTodos()
                    homeViewModel.clearSearchTodos()
                },
                onSortClick = { priority ->
                    homeViewModel.getTodos(priority)
                    homeViewModel.updateIsSortExpanded()
                },
                onDeleteAllClick = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screens.AddTodo.route) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_icon)
                )
            }
        }
    ) { paddingValues ->
        HomeBody(
            uiState = uiState,
            paddingValues = paddingValues,
            onTodoClicked = { id ->
                navController.navigate(Screens.UpdateTodo.passId(id))
            }
        )
    }
}

@Composable
fun HomeBody(
    uiState: HomeUiState,
    paddingValues: PaddingValues,
    onTodoClicked: (Int) -> Unit,
) {
    if (!uiState.isSearchExpanded) {
        HandleListContent(
            todos = uiState.todos,
            paddingValues = paddingValues,
            onTodoClicked = onTodoClicked
        )
    } else {
        HandleListContent(
            todos = uiState.searchTodos,
            paddingValues = paddingValues,
            onTodoClicked = onTodoClicked
        )
    }
}

@Composable
fun HandleListContent(
    todos: List<Todo>,
    paddingValues: PaddingValues,
    onTodoClicked: (Int) -> Unit,
) {
    if (todos.isNotEmpty()) {
        TodoList(
            todos = todos,
            paddingValues = paddingValues,
            onTodoClicked = onTodoClicked
        )
    } else {
        EmptyContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    viewModel: HomeViewModel,
    uiState: HomeUiState,
    onTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onSortClick: (Priority) -> Unit,
    onClearClick: () -> Unit,
    onDeleteAllClick: () -> Unit
) {

    TopAppBar(
        title = { Text(text = stringResource(R.string.tasks)) },
        actions = {
            if (!uiState.isSearchExpanded) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    modifier = Modifier.clickable {
                        viewModel.updateIsSearchExpanded()
                    }
                )
            } else {
                SearchAppBar(
                    text = uiState.searchText,
                    onTextChange = onTextChange,
                    onSearchClick = onSearchClick,
                    onClearClick = onClearClick
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.icon_sort_24),
                contentDescription = stringResource(R.string.sort_icon),
                modifier = Modifier.clickable {
                    viewModel.updateIsSortExpanded()
                }
            )
            if (uiState.isSortExpanded) {
                SortPopupBox(
                    options = options,
                    onSortClick = onSortClick,
                    onDismissPopup = { viewModel.updateIsSortExpanded() }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.more_icon),
                modifier = Modifier.clickable {
                    viewModel.updateIsMoreExpanded()
                }
            )
            if (uiState.isMoreExpanded) {
                DeletePopupBox(
                    onDeleteAllClick = {
                        viewModel.updateIsAlertDialogOpen()
                        viewModel.updateIsMoreExpanded()
                    },
                    onDismissPopup = { viewModel.updateIsMoreExpanded() }
                )
            }
            if (uiState.isAlertDialogOpen) {
                ShowAlertDialog(
                    title = stringResource(id = R.string.remove_all_tasks),
                    description = stringResource(id = R.string.delete_all_alert_dialog_text),
                    onDismissClick = { viewModel.updateIsAlertDialogOpen() },
                    onConfirmClick = onDeleteAllClick,
                    onDismissRequest = { viewModel.updateIsAlertDialogOpen() }
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon),
                modifier = Modifier.alpha(0.5f),
                tint = MaterialTheme.colorScheme.secondaryContainer
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.clear_icon),
                tint = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.clickable {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onClearClick()
                    }
                }
            )
        },
        singleLine = true,
        maxLines = 1,
        placeholder = {
            Text(
                text = stringResource(R.string.search_todos),
                modifier = Modifier.alpha(0.5f),
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick(text)
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.secondaryContainer,
            cursorColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}

@Composable
fun SortPopupBox(
    options: List<SortPopup>,
    onSortClick: (Priority) -> Unit,
    onDismissPopup: () -> Unit,
    modifier: Modifier = Modifier
) {
    Popup(
        onDismissRequest = onDismissPopup,
        alignment = Alignment.TopStart,
        properties = PopupProperties()
    ) {
        Box(
            modifier = modifier
                .padding(top = 36.dp, end = 36.dp)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                options.forEach { option ->
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = stringResource(R.string.circle_icon),
                            tint = option.color
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = option.type.name,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.clickable {
                                onSortClick(option.type)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeletePopupBox(
    onDeleteAllClick: () -> Unit,
    onDismissPopup: () -> Unit,
    modifier: Modifier = Modifier
) {
    Popup(
        onDismissRequest = onDismissPopup
    ) {
        Box(
            modifier = modifier
                .padding(top = 36.dp)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = stringResource(R.string.delete_all),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onDeleteAllClick() }
            )
        }
    }
}



@Composable
fun TodoList(
    todos: List<Todo>,
    onTodoClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues.Absolute(0.dp)
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        items(
            items = todos,
            key = { todo ->
                todo.id
            }
        ) { todoItem ->
            TodoItem(
                todo = todoItem,
                iconColor = todoItem.priority.toIconColor(),
                onTodoClicked = onTodoClicked
            )
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    iconColor: Color,
    onTodoClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onTodoClicked(todo.id)
            }
    ) {
        Icon(
            imageVector = Icons.Default.Circle,
            contentDescription = stringResource(id = R.string.circle_icon),
            tint = iconColor,
            modifier = Modifier.align(Alignment.End)
        )
        Text(
            text = todo.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = todo.description ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.SentimentDissatisfied,
            contentDescription = stringResource(R.string.sad_emoji_icon),
            tint = Color.Gray,
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = "No Tasks Found.",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MyToDoTheme {
        HomeScreen(
            navController = rememberNavController()
        )
    }
}

@Preview
@Composable
fun SortPopupBoxPreview() {
    MyToDoTheme {
        SortPopupBox(
            options = options,
            onSortClick = {},
            onDismissPopup = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteAllAlertDialogPreview() {
    MyToDoTheme {
        ShowAlertDialog(
            title = stringResource(id = R.string.remove_all_tasks),
            description = stringResource(id = R.string.delete_all_alert_dialog_text),
            onDismissClick = {},
            onConfirmClick = {},
            onDismissRequest = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    MyToDoTheme {
        val todo = Todo(
            id = 0,
            title = "Do homework",
            priority = Priority.MEDIUM,
            description = "I need to clean my room"
        )
        TodoItem(
            todo = todo,
            onTodoClicked = {},
            iconColor = Color.Green
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun EmptyContentPreview() {
    MyToDoTheme {
        EmptyContent()
    }
}

