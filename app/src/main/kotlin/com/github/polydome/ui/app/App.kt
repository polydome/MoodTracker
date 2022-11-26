package com.github.polydome.ui.app

import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.polydome.ui.calendar.CalendarViewModel
import com.github.polydome.ui.calendar.MoodCalendar
import com.github.polydome.ui.mood_prompt.MoodPrompt

enum class Tab {
    Button,
    Prompt
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.padding(64.dp)) {
            var tab by remember { mutableStateOf(Tab.Button) }

            MoodCalendar(CalendarViewModel())


            Box {
                Crossfade(targetState = tab, modifier = Modifier.align(Alignment.Center)) { currentTab ->
                    when (currentTab) {
                        Tab.Button -> ButtonView(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize(),
                            switchTab = {
                                tab = Tab.Prompt
                            }
                        )
                        Tab.Prompt -> MoodPrompt(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize(),
                            switchTab = {
                                tab = Tab.Button
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppHeader(text: String) {
    Text(text, fontSize = 32.sp, fontWeight = FontWeight.Light)
}

@Composable
fun ButtonView(modifier: Modifier = Modifier, switchTab: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppHeader("How are you?")
        AppButton(
            icon = Icons.Filled.Add,
            onClick = switchTab
        )
    }
}

@Composable
fun AppButton(modifier: Modifier = Modifier, onClick: () -> Unit, icon: ImageVector) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(32.dp)
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )
    }
}