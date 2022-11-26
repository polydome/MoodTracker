package com.github.polydome.ui.widget

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Header(text: String) {
    Text(text, fontSize = 32.sp, fontWeight = FontWeight.Light)
}