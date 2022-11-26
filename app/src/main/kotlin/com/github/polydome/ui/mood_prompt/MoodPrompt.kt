package com.github.polydome.ui.mood_prompt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.github.polydome.ui.mood_prompt.icons.*
import com.github.polydome.ui.widget.ActionButton
import com.github.polydome.ui.widget.Header


@Composable
fun MoodPrompt(modifier: Modifier = Modifier, switchTab: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Header("Rate your mood")
        MoodPicker()

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
            ActionButton(onClick = {}, icon = Icons.Filled.Done)
        }
    }
}

private data class MoodLevel(
    val value: Int,
    val icon: ImageVector
) {
    init {
        require(value in 0..5)
    }

    companion object {
        val all: Array<MoodLevel>
            get() = arrayOf(
                MoodLevel(0, MoodIcons.Mood0),
                MoodLevel(1, MoodIcons.Mood1),
                MoodLevel(2, MoodIcons.Mood2),
                MoodLevel(3, MoodIcons.Mood3),
                MoodLevel(4, MoodIcons.Mood4),
            )
    }
}

@Composable
private fun MoodPicker() {
    var selectedMoodIndex: Int? by remember { mutableStateOf(null) }

    Row {
        MoodLevel.all.forEachIndexed { index, moodLevel ->
            MoodButton(
                onClick = {
                    selectedMoodIndex = index
                },
                icon = moodLevel.icon,
                selected = selectedMoodIndex == index
            )
        }
    }
}

@Composable
private fun MoodButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean = false,
    icon: ImageVector
) {
    Surface(
        shape = CircleShape,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.background(if (selected) Color.LightGray else Color.Transparent)
        ) {
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
        }
    }
}