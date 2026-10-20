package com.andre.techquiz.presentation.screens.addquestion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.andre.techquiz.presentation.viewmodel.AddQuestionViewModel
import com.andre.techquiz.presentation.viewmodel.AppViewModelFactory

@Composable
fun AddQuestionScreen(
    onSaved: () -> Unit,
    onBack: () -> Unit,
    viewModel: AddQuestionViewModel = viewModel(factory = AppViewModelFactory.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.saved) {
        if (uiState.saved) onSaved()
    }

    AddQuestionContent(
        uiState = uiState,
        onTextChange = viewModel::onTextChange,
        onOptionChange = viewModel::onOptionChange,
        onCorrectSelected = viewModel::onCorrectSelected,
        onCategorySelected = viewModel::onCategorySelected,
        onDifficultySelected = viewModel::onDifficultySelected,
        onSave = viewModel::onSave,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionContent(
    uiState: AddQuestionUiState,
    onTextChange: (String) -> Unit,
    onOptionChange: (Int, String) -> Unit,
    onCorrectSelected: (Int) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onDifficultySelected: (Difficulty) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_question_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Text(text = "←") }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.text,
                onValueChange = onTextChange,
                label = { Text(stringResource(R.string.add_question_text)) },
                isError = uiState.showErrors && uiState.isTextBlank,
                supportingText = {
                    if (uiState.showErrors && uiState.isTextBlank) {
                        Text(stringResource(R.string.error_required))
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.add_question_options_hint),
                style = MaterialTheme.typography.titleSmall
            )

            uiState.options.forEachIndexed { index, option ->
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RadioButton(
                        selected = uiState.correctIndex == index,
                        onClick = { onCorrectSelected(index) }
                    )
                    OutlinedTextField(
                        value = option,
                        onValueChange = { onOptionChange(index, it) },
                        label = { Text(stringResource(R.string.add_question_option, index + 1)) },
                        isError = uiState.showErrors && option.isBlank(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (uiState.showErrors && uiState.hasDuplicateOptions) {
                Text(
                    text = stringResource(R.string.error_duplicate_options),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            ChoiceChips(
                title = stringResource(R.string.filter_category),
                options = Category.entries,
                selected = uiState.category,
                optionLabel = { stringResource(it.labelRes) },
                onSelected = onCategorySelected
            )

            ChoiceChips(
                title = stringResource(R.string.filter_difficulty),
                options = Difficulty.entries,
                selected = uiState.difficulty,
                optionLabel = { stringResource(it.labelRes) },
                onSelected = onDifficultySelected
            )

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.add_question_save))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun <T> ChoiceChips(
    title: String,
    options: List<T>,
    selected: T,
    optionLabel: @Composable (T) -> String,
    onSelected: (T) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                FilterChip(
                    selected = option == selected,
                    onClick = { onSelected(option) },
                    label = { Text(optionLabel(option)) }
                )
            }
        }
    }
}