package com.github.polydome.ui.calendar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.polydome.ui.mood_prompt.icons.*

const val HUE_LOWEST = 0f
const val HUE_HIGHEST = 100f
const val SATURATION_BASE = 1f
const val LIGHTNESS_BASE = 0.5f
const val SCORE_LOWEST = 1
const val SCORE_HIGHEST = 5
const val bullet = "\u2022"

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
                        DayCard(day)
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DayCard(day: CalendarState.Day?) {
    TooltipArea(tooltip = {
        Surface(
            modifier = Modifier.shadow(4.dp), shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                DayStatistics(day)
                DayEmotions(day)
            }
        }
    }) {
        Card(
            modifier = Modifier.padding(6.dp).width(60.dp).height(60.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = if (day != null) 4.dp else 0.dp
        ) {
            if (day == null) {
                return@Card Box(
                    modifier = Modifier.background(color = Color.LightGray)
                )
            }

            Box(
                Modifier.background(color = if (day.lastMood != null) getMoodScoreColor(day.lastMood) else Color.Transparent),
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

@Composable
private fun DayStatistics(day: CalendarState.Day?) {
    Column {
        day?.lowestMood?.let {
            Row {
                Text("Lowest mood: ")
                MoodIcon(it)
            }
        }

        day?.highestMood?.let {
            Row {
                Text("Top mood: ")
                MoodIcon(it)
            }
        }

        day?.averageMood?.let {
            Row {
                Text("Average mood: ")
                MoodIcon(it)
            }
        }

        day?.lastMood?.let {
            Row {
                Text("Last mood: ")
                MoodIcon(it)
            }
        }
    }
}

@Composable
private fun DayEmotions(day: CalendarState.Day?) {
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 22.sp))

    day?.emotions?.let { emotions ->
        if (emotions.isEmpty()) {
            return@let
        }

        Column {
            Text("Emotions:")
            Text(
                text = buildAnnotatedString {
                    emotions.forEach { emotion ->
                        withStyle(style = paragraphStyle) {
                            append(bullet)
                            append("  ")
                            append(emotion)
                        }
                    }
                }, modifier = Modifier.padding(10.dp)
            )
        }
    }
}

private fun getMoodScoreColor(score: Int): Color {
    val hue = (((HUE_HIGHEST - HUE_LOWEST) * (score - SCORE_LOWEST)) / (SCORE_HIGHEST - SCORE_LOWEST)) + HUE_LOWEST
    return Color.hsl(hue, SATURATION_BASE, LIGHTNESS_BASE)
}

@Composable
private fun MoodIcon(level: Int) {
    Icon(
        imageVector = when (level) {
            1 -> MoodIcons.Mood0
            2 -> MoodIcons.Mood1
            3 -> MoodIcons.Mood2
            4 -> MoodIcons.Mood3
            5 -> MoodIcons.Mood4
            else -> Icons.Filled.Warning
        }, contentDescription = null
    )
}
