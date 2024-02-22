package com.example.mytodo.util

import androidx.compose.ui.graphics.Color
import com.example.mytodo.models.Priority


fun Priority.toIconColor(): Color {
    return when(this) {
        Priority.LOW -> Color.Green
        Priority.MEDIUM -> Color.Yellow
        Priority.HIGH -> Color.Red
        Priority.NONE -> Color.Gray
    }
}