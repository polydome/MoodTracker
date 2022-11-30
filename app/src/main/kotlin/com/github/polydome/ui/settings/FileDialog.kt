package com.github.polydome.ui.settings

import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

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

fun promptJson(): File {
    val fileChooser = JFileChooser("/").apply {
        fileFilter = FileNameExtensionFilter("JSON files", "json")
        fileSelectionMode = JFileChooser.FILES_ONLY
        dialogTitle = "Select a file"
        approveButtonText = "Select"
        approveButtonToolTipText = "Select a backup file"
    }
    fileChooser.showOpenDialog(null)
    return fileChooser.selectedFile
}