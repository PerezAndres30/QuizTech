package com.andre.techquiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andre.techquiz.R

private const val LOW_TIME_SECONDS = 5

@Composable
fun QuestionHeader(
    currentIndex: Int,
    total: Int,
    secondsLeft: Int,
    modifier: Modifier = Modifier
) {
    val timeColor = if (secondsLeft <= LOW_TIME_SECONDS) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }
    val progress = if (total > 0) (currentIndex + 1) / total.toFloat() else 0f

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.question_progress, currentIndex + 1, total),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.seconds_left, secondsLeft),
                style = MaterialTheme.typography.titleMedium,
                color = timeColor
            )
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )
    }
}