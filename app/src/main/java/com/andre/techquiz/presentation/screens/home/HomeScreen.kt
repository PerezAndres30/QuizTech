package com.andre.techquiz.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andre.techquiz.R
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.presentation.components.AppIconButton
import com.andre.techquiz.presentation.components.FilterChipRow
import com.andre.techquiz.presentation.components.PrimaryButton
import com.andre.techquiz.presentation.viewmodel.AppViewModelFactory
import com.andre.techquiz.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onStartGame: (Category?, Difficulty?) -> Unit,
    onOpenHistory: () -> Unit,
    onAddQuestion: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelFactory.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected,
        onDifficultySelected = viewModel::onDifficultySelected,
        onStartClick = { onStartGame(uiState.selectedCategory, uiState.selectedDifficulty) },
        onHistoryClick = onOpenHistory,
        onAddQuestionClick = onAddQuestion
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onCategorySelected: (Category?) -> Unit,
    onDifficultySelected: (Difficulty?) -> Unit,
    onStartClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onAddQuestionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AppIconButton(
                        iconRes = R.drawable.ic_history,
                        contentDescription = stringResource(R.string.cd_history),
                        onClick = onHistoryClick
                    )
                    AppIconButton(
                        iconRes = R.drawable.ic_add,
                        contentDescription = stringResource(R.string.cd_add_question),
                        onClick = onAddQuestionClick
                    )
                }

                Text(
                    text = stringResource(R.string.home_subtitle),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 22.sp),
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

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.availableQuestions.toString(),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = stringResource(R.string.available_questions),
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = stringResource(R.string.start_game),
                onClick = onStartClick,
                enabled = uiState.availableQuestions > 0
            )
        }
    }
}