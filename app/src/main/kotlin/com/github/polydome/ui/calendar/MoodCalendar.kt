package com.github.polydome.ui.calendar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    Box(Modifier.fillMaxWidth()) {
        Column(Modifier.align(Alignment.Center)) {
            state.weeks.forEach { week ->
                LazyRow {
                    items(week.size) { dayIndex ->
                        val day = week[dayIndex]
                        Card(
                            modifier = Modifier
                                .padding(6.dp)
                                .width(60.dp)
                                .height(60.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = if (day != null) 4.dp else 0.dp
                        ) {
                            if (day == null) {
                                return@Card Box(
                                    modifier = Modifier.background(color = Color.LightGray)
                                )
                            }

                            Box(
                                Modifier
                                    .background(color = if (day.value != null) getMoodScoreColor(day.value) else Color.Transparent),
                            ) {
                                Text(
                                    text = " ${day.number}",
                                    modifier = Modifier.align(Alignment.Center),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Light
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

private fun getMoodScoreColor(score: Int): Color {
    val hue =
        (((HUE_HIGHEST - HUE_LOWEST) * (score - SCORE_LOWEST)) / (SCORE_HIGHEST - SCORE_LOWEST)) + HUE_LOWEST
    return Color.hsl(hue, SATURATION_BASE, LIGHTNESS_BASE)
}
