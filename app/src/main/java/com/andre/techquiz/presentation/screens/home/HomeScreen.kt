package com.andre.techquiz.presentation.screens.home

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andre.techquiz.R
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.presentation.components.FilterChipRow
import com.andre.techquiz.presentation.viewmodel.HomeViewModel
import com.andre.techquiz.presentation.viewmodel.AppViewModelFactory
import androidx.compose.material3.OutlinedButton

@Composable
fun HomeScreen(
    onStartGame: (Category?, Difficulty?) -> Unit,
    onOpenHistory: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelFactory.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected,
        onDifficultySelected = viewModel::onDifficultySelected,
        onStartClick = { onStartGame(uiState.selectedCategory, uiState.selectedDifficulty) },
        onHistoryClick = onOpenHistory
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onCategorySelected: (Category?) -> Unit,
    onDifficultySelected: (Difficulty?) -> Unit,
    onStartClick: () -> Unit,
    onHistoryClick: () -> Unit,
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
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.home_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            FilterChipRow(
                title = stringResource(R.string.filter_category),
                allLabel = stringResource(R.string.filter_all),
                options = Category.entries,
                selected = uiState.selectedCategory,
                optionLabel = { stringResource(it.labelRes) },
                onSelected = onCategorySelected
            )

            FilterChipRow(
                title = stringResource(R.string.filter_difficulty),
                allLabel = stringResource(R.string.filter_all),
                options = Difficulty.entries,
                selected = uiState.selectedDifficulty,
                optionLabel = { stringResource(it.labelRes) },
                onSelected = onDifficultySelected
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = uiState.availableQuestions.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = stringResource(R.string.available_questions),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onHistoryClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.view_history))
            }

            Button(
                onClick = onStartClick,
                enabled = uiState.availableQuestions > 0,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.start_game))
            }
        }
    }
}