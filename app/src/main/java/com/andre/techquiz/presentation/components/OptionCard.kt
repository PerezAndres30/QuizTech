package com.andre.techquiz.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andre.techquiz.R
import com.andre.techquiz.presentation.theme.CorrectColor
import com.andre.techquiz.presentation.theme.CorrectContainer

enum class OptionState { Idle, Correct, Wrong }

@Composable
fun OptionCard(
    letter: String,
    text: String,
    state: OptionState,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val containerColor = when (state) {
        OptionState.Idle -> colors.surface
        OptionState.Correct -> CorrectContainer
        OptionState.Wrong -> colors.errorContainer
    }
    val accentColor = when (state) {
        OptionState.Idle -> colors.outline
        OptionState.Correct -> CorrectColor
        OptionState.Wrong -> colors.error
    }
    val badgeColor = if (state == OptionState.Idle) colors.surfaceVariant else accentColor
    val badgeTextColor = if (state == OptionState.Idle) colors.onSurfaceVariant else Color.White

    Surface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = containerColor,
        border = BorderStroke(if (state == OptionState.Idle) 1.dp else 2.dp, accentColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(badgeColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 13.sp),
                    color = badgeTextColor
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onSurface,
                modifier = Modifier.weight(1f)
            )
            when (state) {
                OptionState.Correct -> Icon(
                    painter = painterResource(R.drawable.ic_option_correct),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = CorrectColor
                )
                OptionState.Wrong -> Icon(
                    painter = painterResource(R.drawable.ic_option_wrong),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = colors.error
                )
                OptionState.Idle -> Unit
            }
        }
    }
}