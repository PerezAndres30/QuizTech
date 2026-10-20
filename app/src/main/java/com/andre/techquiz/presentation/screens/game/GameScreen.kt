package com.andre.techquiz.presentation.screens.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andre.techquiz.R
import com.andre.techquiz.presentation.components.OptionCard
import com.andre.techquiz.presentation.components.OptionState
import com.andre.techquiz.presentation.components.QuestionHeader
import com.andre.techquiz.presentation.components.ScoreChip
import com.andre.techquiz.presentation.viewmodel.AppViewModelFactory
import com.andre.techquiz.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(
    onFinished: () -> Unit,
    onBack: () -> Unit,
    viewModel: GameViewModel = viewModel(factory = AppViewModelFactory .Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isFinished) {
        if (uiState.isFinished) onFinished()
    }

    GameContent(
        uiState = uiState,
        onOptionSelected = viewModel::onOptionSelected,
        onNext = viewModel::onNext,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameContent(
    uiState: GameUiState,
    onOptionSelected: (Int) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.game_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text(text = "←")
                    }
                }
            )
        }
    ) { innerPadding ->
        val question = uiState.currentQuestion
        if (question != null) {
            val answered = uiState.answerStatus != AnswerStatus.Pending
            val isLast = uiState.currentIndex == uiState.total - 1

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ScoreChip(label = stringResource(R.string.score_label), value = uiState.score)
                    ScoreChip(label = stringResource(R.string.streak_label), value = uiState.streak)
                }

                QuestionHeader(
                    currentIndex = uiState.currentIndex,
                    total = uiState.total,
                    secondsLeft = uiState.secondsLeft
                )

                Text(
                    text = question.text,
                    style = MaterialTheme.typography.titleLarge
                )

                question.options.forEachIndexed { index, option ->
                    OptionCard(
                        text = option,
                        state = optionState(index, question.correctIndex, uiState),
                        enabled = !answered,
                        onClick = { onOptionSelected(index) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (answered) {
                    Button(
                        onClick = onNext,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(
                                if (isLast) R.string.finish_game else R.string.next_question
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun optionState(index: Int, correctIndex: Int, uiState: GameUiState): OptionState =
    when {
        uiState.answerStatus == AnswerStatus.Pending -> OptionState.Idle
        index == correctIndex -> OptionState.Correct
        index == uiState.selectedOption -> OptionState.Wrong
        else -> OptionState.Idle
    }