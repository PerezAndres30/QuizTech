package com.andre.techquiz.presentation.screens.addquestion

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andre.techquiz.R
import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.presentation.components.AppIconButton
import com.andre.techquiz.presentation.components.AppTextField
import com.andre.techquiz.presentation.components.FilterChipRow
import com.andre.techquiz.presentation.components.PrimaryButton
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
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding()
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
                        .height(64.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AppIconButton(
                        iconRes = R.drawable.ic_back,
                        contentDescription = stringResource(R.string.cd_back),
                        onClick = onBack
                    )
                    Text(
                        text = stringResource(R.string.add_question_title),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = stringResource(R.string.add_question_statement),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    AppTextField(
                        value = uiState.text,
                        onValueChange = onTextChange,
                        placeholder = stringResource(R.string.add_question_statement_hint),
                        isError = uiState.showErrors && uiState.isTextBlank,
                        singleLine = false
                    )
                    if (uiState.showErrors && uiState.isTextBlank) {
                        ErrorText(stringResource(R.string.error_required))
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = stringResource(R.string.add_question_options_hint),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    uiState.options.forEachIndexed { index, option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            RadioMark(
                                selected = uiState.correctIndex == index,
                                onClick = { onCorrectSelected(index) }
                            )
                            AppTextField(
                                value = option,
                                onValueChange = { onOptionChange(index, it) },
                                placeholder = stringResource(R.string.add_question_option, index + 1),
                                isError = uiState.showErrors && option.isBlank(),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    if (uiState.showErrors && uiState.hasDuplicateOptions) {
                        ErrorText(stringResource(R.string.error_duplicate_options))
                    }
                }

                FilterChipRow(
                    title = stringResource(R.string.filter_category),
                    options = Category.entries,
                    selected = uiState.category,
                    optionLabel = { stringResource(it.labelRes) },
                    onSelected = { it?.let(onCategorySelected) }
                )

                FilterChipRow(
                    title = stringResource(R.string.filter_difficulty),
                    options = Difficulty.entries,
                    selected = uiState.difficulty,
                    optionLabel = { stringResource(it.labelRes) },
                    onSelected = { it?.let(onDifficultySelected) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = stringResource(R.string.add_question_save),
                onClick = onSave
            )
        }
    }
}

@Composable
private fun RadioMark(selected: Boolean, onClick: () -> Unit) {
    val primary = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .selectable(selected = selected, onClick = onClick, role = Role.RadioButton),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    width = 2.dp,
                    color = if (selected) primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(primary, CircleShape)
                )
            }
        }
    }
}

@Composable
private fun ErrorText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error
    )
}