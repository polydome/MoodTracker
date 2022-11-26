package com.github.polydome.ui.mood_prompt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.polydome.ui.app.AppButton
import com.github.polydome.ui.app.AppHeader


@Composable
fun MoodPrompt(modifier: Modifier = Modifier, switchTab: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppHeader("Rate your mood")
        AppHeader("Select your emotions")
        Text("Emotions")

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .width(240.dp)
        ) {
            AppButton(onClick = switchTab, icon = Icons.Filled.Close)
            AppButton(onClick = {}, icon = Icons.Filled.Done)
        }
    }
}