package com.andre.techquiz.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class OptionState { Idle, Correct, Wrong }

private val CorrectGreen = Color(0xFF2E7D32)
private val CorrectGreenContainer = Color(0xFFC8E6C9)

@Composable
fun OptionCard(
    text: String,
    state: OptionState,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when (state) {
        OptionState.Idle -> MaterialTheme.colorScheme.surface
        OptionState.Correct -> CorrectGreenContainer
        OptionState.Wrong -> MaterialTheme.colorScheme.errorContainer
    }
    val borderColor = when (state) {
        OptionState.Idle -> MaterialTheme.colorScheme.outline
        OptionState.Correct -> CorrectGreen
        OptionState.Wrong -> MaterialTheme.colorScheme.error
    }
    val contentColor = if (state == OptionState.Idle) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 1f)
    }

    Card(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        ),
        border = BorderStroke(width = 1.dp, color = borderColor)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}