package com.github.polydome.ui.mood_prompt

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.polydome.ui.widget.ActionButton
import com.github.polydome.ui.widget.Header


@Composable
fun MoodPrompt(modifier: Modifier = Modifier, switchTab: () -> Unit, viewModel: MoodPromptViewModel = MoodPromptViewModel()) {
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
        Text("Emotions")

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
