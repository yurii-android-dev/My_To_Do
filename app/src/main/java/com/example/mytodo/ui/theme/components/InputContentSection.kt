package com.example.mytodo.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mytodo.R
import com.example.mytodo.data.LocalSortPopupData
import com.example.mytodo.models.Priority
import com.example.mytodo.util.toIconColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputContentSection(
    modifier: Modifier = Modifier,
    titleText: String,
    titleTextChanged: (String) -> Unit,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    priority: Priority,
    onDismissRequest: () -> Unit,
    onDropdownMenuItemClicked: (Priority) -> Unit,
    descriptionText: String?,
    descriptionTextChanged: (String) -> Unit,
) {

    val options = LocalSortPopupData.options.filter{ option ->
        option != LocalSortPopupData.options.last()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        OutlinedTextField(
            value = titleText,
            onValueChange = titleTextChanged,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.title_label)) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = onExpandedChange,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = priority.name,
                onValueChange = {},
                readOnly = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = stringResource(id = R.string.circle_icon),
                        tint = priority.toIconColor()
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.exposedDropdownSize()
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option.type.name ) },
                        onClick = { onDropdownMenuItemClicked(option.type) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = stringResource(id = R.string.circle_icon),
                                tint = option.color
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = descriptionText ?: "",
            onValueChange = descriptionTextChanged,
            label = { Text(text = stringResource(R.string.description_label)) },
            modifier = Modifier.fillMaxSize()
        )
    }
}