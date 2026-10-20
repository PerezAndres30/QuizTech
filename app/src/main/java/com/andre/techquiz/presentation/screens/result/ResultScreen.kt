package com.andre.techquiz.presentation.screens.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andre.techquiz.R
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.presentation.viewmodel.AppViewModelFactory
import com.andre.techquiz.presentation.viewmodel.ResultViewModel

@Composable
fun ResultScreen(
    onPlayAgain: (Category?, Difficulty?) -> Unit,
    onGoHome: () -> Unit,
    viewModel: ResultViewModel = viewModel(factory = AppViewModelFactory.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ResultContent(
        uiState = uiState,
        onPlayAgain = { onPlayAgain(uiState.categoryFilter, uiState.difficultyFilter) },
        onGoHome = onGoHome
    )
}

@Composable
fun ResultContent(
    uiState: ResultUiState,
    onPlayAgain: () -> Unit,
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.result_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            if (!uiState.found) {
                Text(
                    text = stringResource(R.string.result_not_found),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                if (uiState.isNewRecord) {
                    Text(
                        text = stringResource(R.string.result_new_record),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.score.toString(),
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = stringResource(R.string.result_points),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatRow(
                            label = stringResource(R.string.result_correct),
                            value = stringResource(
                                R.string.result_correct_value,
                                uiState.correctAnswers,
                                uiState.totalQuestions
                            )
                        )
                        StatRow(
                            label = stringResource(R.string.result_accuracy),
                            value = stringResource(R.string.result_accuracy_value, uiState.accuracyPercent)
                        )
                        StatRow(
                            label = stringResource(R.string.result_max_streak),
                            value = uiState.maxStreak.toString()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onPlayAgain,
                enabled = uiState.found,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.play_again))
            }
            OutlinedButton(
                onClick = onGoHome,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.go_home))
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}