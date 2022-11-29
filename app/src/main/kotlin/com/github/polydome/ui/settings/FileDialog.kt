package com.github.polydome.ui.settings

import java.io.File
import javax.swing.JFileChooser

fun promptDirectory(): File {
    val fileChooser = JFileChooser("/").apply {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        dialogTitle = "Select a folder"
        approveButtonText = "Select"
        approveButtonToolTipText = "Select current directory as save destination"
    }
    fileChooser.showOpenDialog(null)
    return fileChooser.selectedFile
}