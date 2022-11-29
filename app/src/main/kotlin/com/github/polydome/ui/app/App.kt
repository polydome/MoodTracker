package com.github.polydome.ui.app

import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.polydome.ui.calendar.CalendarViewModel
import com.github.polydome.ui.calendar.MoodCalendar
import com.github.polydome.ui.mood_prompt.MoodPrompt
import com.github.polydome.ui.mood_prompt.MoodPromptViewModel
import com.github.polydome.ui.settings.Notice
import com.github.polydome.ui.settings.SettingsButton
import com.github.polydome.ui.settings.SettingsViewModel
import com.github.polydome.ui.widget.ActionButton
import com.github.polydome.ui.widget.Header
import kotlinx.coroutines.launch
import java.io.File
import javax.swing.JFileChooser

enum class Tab {
    Button,
    Prompt
}

@Composable
@Preview
fun App() {
    val settingsViewModel = SettingsViewModel(::promptDirectory)
    val visibleNotices = remember { mutableStateListOf<String>() }
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        settingsViewModel.notices.collect {
            visibleNotices.add(it)
        }
    }

    MaterialTheme {
        Box {

            SettingsButton(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                settingsViewModel
            )

            Column(Modifier.padding(64.dp)) {
                var tab by remember { mutableStateOf(Tab.Button) }

                MoodCalendar(CalendarViewModel())

                Box {
                    Crossfade(targetState = tab, modifier = Modifier.align(Alignment.Center)) { currentTab ->
                        when (currentTab) {
                            Tab.Button -> ButtonView(
                                modifier = Modifier
                                    .fillMaxSize(),
                                switchTab = {
                                    tab = Tab.Prompt
                                }
                            )
                            Tab.Prompt -> MoodPrompt(
                                modifier = Modifier
                                    .fillMaxSize(),
                                switchTab = {
                                    tab = Tab.Button
                                },
                                MoodPromptViewModel()
                            )
                        }
                    }
                }
            }

            LazyColumn (
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (notice in visibleNotices) {
                    item {
                        Notice(text = notice, onClick = {
                            visibleNotices.remove(notice)
                        })
                    }

                }
            }
        }
    }
}

@Composable
fun ButtonView(modifier: Modifier = Modifier, switchTab: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header("How are you?")
        ActionButton(
            icon = Icons.Filled.Add,
            onClick = switchTab
        )
    }
}

private fun promptDirectory(): File {
    val fileChooser = JFileChooser("/").apply {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        dialogTitle = "Select a folder"
        approveButtonText = "Select"
        approveButtonToolTipText = "Select current directory as save destination"
    }
    fileChooser.showOpenDialog(null)
    return fileChooser.selectedFile
}

