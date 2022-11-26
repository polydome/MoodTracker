package com.github.polydome.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.polydome.ui.app.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
