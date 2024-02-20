package com.example.mytodo.data

import androidx.compose.ui.graphics.Color
import com.example.mytodo.models.Priority
import com.example.mytodo.models.SortPopup

object LocalSortPopupData {

    val options = listOf(
        SortPopup(
            type = Priority.LOW,
            color = Color.Green
        ),
        SortPopup(
            type = Priority.MEDIUM,
            color = Color.Yellow
        ),
        SortPopup(
            type = Priority.HIGH,
            color = Color.Red
        ),
        SortPopup(
            type = Priority.NONE,
            color = Color.Gray
        )
    )

}