package com.github.polydome.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

const val HUE_LOWEST = 0f
const val HUE_HIGHEST = 100f
const val SATURATION_BASE = 1f
const val LIGHTNESS_BASE = 0.5f
const val SCORE_LOWEST = 1
const val SCORE_HIGHEST = 5

@Composable
@Preview
fun MoodCalendar(viewModel: CalendarViewModel) {
    val state by viewModel.state.collectAsState()
    val numbers = (1 .. state.lastDayOfMonth).toList()

    Box(Modifier.width(60.dp * 7)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
        ) {
            items(numbers.size) {
                Column {
                    val score = state.dayToMoodScore[it]
                    Box(modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .background(color = score?.let { getMoodScoreColor(it) } ?: Color.Transparent)
                        .border(1.dp, Color.Black, RectangleShape)) {
                        Text(text = "  ${numbers[it]}", modifier = Modifier.align(Alignment.TopEnd))
                        score?.let { score ->
                            Text(text = "$score", modifier = Modifier.align(Alignment.BottomStart))
                        }
                    }
                }
            }
        }
    }
}

private fun getMoodScoreColor(score: Int): Color {
    val hue = (((HUE_HIGHEST - HUE_LOWEST) * (score - SCORE_LOWEST)) / (SCORE_HIGHEST - SCORE_LOWEST)) + HUE_LOWEST
    return Color.hsl(hue, SATURATION_BASE, LIGHTNESS_BASE)
}
