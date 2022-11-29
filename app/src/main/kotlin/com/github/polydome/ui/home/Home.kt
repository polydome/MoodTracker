package com.github.polydome.ui.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.polydome.data.MoodRepositorySqlite
import com.github.polydome.ui.calendar.CalendarViewModel
import com.github.polydome.ui.calendar.MoodCalendar
import com.github.polydome.ui.event.DataEvent
import com.github.polydome.ui.event.EventBus
import com.github.polydome.ui.mood_prompt.MoodFormViewModel
import com.github.polydome.ui.mood_prompt.MoodPrompt
import com.github.polydome.ui.settings.NoticesView
import com.github.polydome.ui.settings.SettingsButton
import com.github.polydome.ui.settings.SettingsViewModel
import com.github.polydome.ui.settings.promptDirectory
import com.github.polydome.usecase.GetScoresBreakdown
import com.github.polydome.usecase.ReportMood

@Composable
@Preview
fun App() {
    val moodRepository = MoodRepositorySqlite()
    val settingsViewModel = SettingsViewModel(::promptDirectory)
    val dataEventBus = EventBus<DataEvent>()
    val calendarViewModel = CalendarViewModel(
        GetScoresBreakdown(moodRepository),
        dataEventBus.events
    )
    val moodFormViewModel = MoodFormViewModel(
        ReportMood(moodRepository),
        dataEventBus.sink
    )

    MaterialTheme {
        Box {
            SettingsButton(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                settingsViewModel = settingsViewModel
            )

            Column(Modifier.padding(64.dp)) {
                MoodCalendar(calendarViewModel)
                MoodPrompt(moodFormViewModel)
            }

            NoticesView(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
                newNotices = settingsViewModel.notices
            )
        }
    }
}

