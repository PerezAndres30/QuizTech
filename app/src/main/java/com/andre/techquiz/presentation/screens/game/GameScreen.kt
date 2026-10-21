package com.andre.techquiz.presentation.screens.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andre.techquiz.R
import com.andre.techquiz.data.model.Question
import com.andre.techquiz.presentation.components.AppIconButton
import com.andre.techquiz.presentation.components.OptionCard
import com.andre.techquiz.presentation.components.OptionState
import com.andre.techquiz.presentation.components.PrimaryButton
import com.andre.techquiz.presentation.components.StatCard
import com.andre.techquiz.presentation.viewmodel.AppViewModelFactory
import com.andre.techquiz.presentation.viewmodel.GameViewModel
import java.util.Locale

@Composable
fun GameScreen(
    onFinished: (String?) -> Unit,
    onBack: () -> Unit,
    viewModel: GameViewModel = viewModel(factory = AppViewModelFactory.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isFinished) {
        if (uiState.isFinished) onFinished(uiState.finishedResultId)
    }

    GameContent(
        uiState = uiState,
        onOptionSelected = viewModel::onOptionSelected,
        onNext = viewModel::onNext,
        onBack = onBack
    )
}

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
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        val question = uiState.currentQuestion
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
        ) {
            if (question != null) {
                val answered = uiState.answerStatus != AnswerStatus.Pending
                val isLast = uiState.currentIndex == uiState.total - 1

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GameTopBar(
                        currentIndex = uiState.currentIndex,
                        total = uiState.total,
                        secondsLeft = uiState.secondsLeft,
                        onBack = onBack
                    )

                    GameProgressBar(progress = (uiState.currentIndex + 1) / uiState.total.toFloat())

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatCard(
                            label = stringResource(R.string.score_label),
                            value = uiState.score.toString(),
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            label = stringResource(R.string.streak_label),
                            value = uiState.streak.toString(),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    QuestionCard(question)

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        question.options.forEachIndexed { index, option ->
                            OptionCard(
                                letter = ('A' + index).toString(),
                                text = option,
                                state = optionState(index, question.correctIndex, uiState),
                                enabled = !answered,
                                onClick = { onOptionSelected(index) }
                            )
                        }
                    }
                }

                if (answered) {
                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButton(
                        text = stringResource(
                            if (isLast) R.string.finish_game else R.string.next_question
                        ),
                        onClick = onNext
                    )
                }
            }
        }
    }
}

@Composable
private fun GameTopBar(
    currentIndex: Int,
    total: Int,
    secondsLeft: Int,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIconButton(
            iconRes = R.drawable.ic_close,
            contentDescription = stringResource(R.string.cd_exit),
            onClick = onBack
        )
        Text(
            text = stringResource(R.string.question_progress, currentIndex + 1, total),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        TimerPill(secondsLeft)
    }
}

@Composable
private fun TimerPill(secondsLeft: Int) {
    Surface(
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_timer),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = String.format(Locale.US, "%d:%02d", secondsLeft / 60, secondsLeft % 60),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun GameProgressBar(progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
private fun QuestionCard(question: Question) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = stringResource(
                        R.string.question_tag,
                        stringResource(question.category.labelRes),
                        stringResource(question.difficulty.labelRes)
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Text(
                text = question.text,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
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