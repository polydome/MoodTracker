package com.github.polydome.ui.mood_prompt

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.unit.dp
import com.github.polydome.ui.widget.ActionButton
import com.github.polydome.ui.widget.Header
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun MoodForm(
    modifier: Modifier = Modifier,
    switchTab: () -> Unit,
    viewModel: MoodFormViewModel
) {
    val state: MoodFormState by viewModel.state.collectAsState(MoodFormState(value = null, emptyList()))

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Header("Rate your mood")
        MoodPicker(
            onMoodPicked = viewModel::selectMoodValue
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Header("Select your emotions")
        EmotionsPicker(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .align(Alignment.CenterHorizontally),
            emotions = state.emotions,
            onEmotionSelected = viewModel::toggleEmotion
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        CustomEmotionPrompt(
            onSubmit = viewModel::addEmotion
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .width(240.dp)
        ) {
            ActionButton(onClick = switchTab, icon = Icons.Filled.Close)
            ActionButton(onClick = {
                viewModel.submitPrompt()
                switchTab()
            }, icon = Icons.Filled.Done)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EmotionsPicker(
    modifier: Modifier = Modifier,
    emotions: List<MoodFormState.Emotion>,
    onEmotionSelected: (index: Int) -> Unit
) {
    FlowRow(
        modifier = modifier,
        mainAxisAlignment = FlowMainAxisAlignment.Center
    ) {
        emotions.forEachIndexed { index, emotion ->
            Chip(
                onClick = {
                    onEmotionSelected(index)
                }, colors = ChipDefaults.outlinedChipColors(
                    backgroundColor = if (emotion.selected) Color.LightGray else Color.Transparent
                )
            ) {
                Text(emotion.name)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CustomEmotionPrompt(onSubmit: (emotionName: String) -> Unit) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier.onPreviewKeyEvent {
            if (it.key == Key.Enter) {
                onSubmit(text)
                text = ""
                return@onPreviewKeyEvent true
            }
            return@onPreviewKeyEvent false
        },
        value = text,
        onValueChange = { value: String -> text = value },
        maxLines = 1,
        placeholder = { Text("Other emotion...") }
    )
}
