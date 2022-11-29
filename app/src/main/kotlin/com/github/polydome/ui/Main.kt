package com.github.polydome.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.polydome.ui.app.App
import javax.swing.UIManager

fun main() = application {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
