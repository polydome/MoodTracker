package com.github.polydome.ui.mood_prompt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.polydome.ui.mood_prompt.icons.*

@Composable
fun MoodPicker(onMoodPicked: (value: Int) -> Unit) {
    var selectedMoodIndex: Int? by remember { mutableStateOf(null) }

    Row {
        MoodLevel.all.forEachIndexed { index, moodLevel ->
            MoodButton(
                onClick = {
                    selectedMoodIndex = index
                    onMoodPicked(moodLevel.value)
                },
                icon = moodLevel.icon,
                selected = selectedMoodIndex == index
            )
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
                MoodLevel(1, MoodIcons.Mood0),
                MoodLevel(2, MoodIcons.Mood1),
                MoodLevel(3, MoodIcons.Mood2),
                MoodLevel(4, MoodIcons.Mood3),
                MoodLevel(5, MoodIcons.Mood4),
            )
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