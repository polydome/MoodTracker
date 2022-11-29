package com.github.polydome.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.polydome.ui.mood_prompt.MoodPrompt
import com.github.polydome.ui.mood_prompt.MoodPromptViewModel
import com.github.polydome.ui.widget.ActionButton
import com.github.polydome.ui.widget.Header

private enum class Tab {
    Button,
    Prompt
}

@Composable
fun MoodForm() {
    Box {
        var tab by remember { mutableStateOf(Tab.Button) }

        Crossfade(
            targetState = tab,
            modifier = Modifier.align(Alignment.Center)
        ) { currentTab ->
            when (currentTab) {
                Tab.Button -> MoodButton(
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

@Composable
private fun MoodButton(modifier: Modifier = Modifier, switchTab: () -> Unit) {
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