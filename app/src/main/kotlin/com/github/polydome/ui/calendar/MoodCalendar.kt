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

fun testWeeks(): Array<Array<Int?>> {
    return arrayOf(
        arrayOf(null, null, null, null, 1, 2, 3),
        arrayOf(4, 5, 6, 7, 8, 9, 10),
        arrayOf(11, 12, 13, 14, 15, 16, 17),
        arrayOf(18, 19, 20, 21, 22, 23, 24),
        arrayOf(25, 26, 27, 28, 29, 30, null),
    )
}

@Composable
@Preview
fun MoodCalendar(viewModel: CalendarViewModel) {
    val state by viewModel.state.collectAsState()
    val numbers = (1..state.lastDayOfMonth + 1).toList()

    Box(Modifier.fillMaxWidth()) {
        Column(Modifier.align(Alignment.Center)) {
            testWeeks().forEach { week ->
                LazyRow {
                    items(week.size) { dayIndex ->
                        val index = week[dayIndex]

                        val score = state.dayToMoodScore[index]
                        Card(
                            modifier = Modifier
                                .padding(6.dp)
                                .width(60.dp)
                                .height(60.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = if (index != null) 4.dp else 0.dp
                        ) {
                            if (index == null) {
                                return@Card Box(
                                    modifier = Modifier.background(color = Color.LightGray)
                                )
                            }

                            Box(
                                Modifier
                                    .background(color = score?.let { score -> getMoodScoreColor(score) } ?: Color.Transparent),
                            ) {
                                Text(text = " ${numbers[index] - 1}", modifier = Modifier.align(Alignment.Center), fontSize = 24.sp, fontWeight = FontWeight.Light)
                            }
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
