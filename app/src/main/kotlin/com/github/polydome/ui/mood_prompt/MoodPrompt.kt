package com.github.polydome.ui.mood_prompt

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.polydome.ui.widget.ActionButton
import com.github.polydome.ui.widget.Header
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun MoodPrompt(
    modifier: Modifier = Modifier,
    switchTab: () -> Unit,
    viewModel: MoodPromptViewModel
) {
    val state: MoodPromptState by viewModel.state.collectAsState(MoodPromptState(value = null, emptyList()))

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
            modifier = Modifier.height(32.dp)
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
            modifier = Modifier.height(64.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .width(240.dp)
        ) {
            ActionButton(onClick = switchTab, icon = Icons.Filled.Close)
            ActionButton(onClick = viewModel::submitPrompt, icon = Icons.Filled.Done)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmotionsPicker(modifier: Modifier = Modifier, emotions: List<MoodPromptState.Emotion>, onEmotionSelected: (index: Int) -> Unit) {
    FlowRow (
        modifier = modifier
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
