package com.github.polydome.ui.settings

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SettingsViewModel(private val promptDirectory: () -> File) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _notices = MutableSharedFlow<String>()
    val notices: Flow<String> = _notices

    fun exportToJson() {
        val directory = promptDirectory()
        val currentDateTime = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("dd-MM-uu_hh:mm")
        )
        val filePath = "$directory/mood_tracker_backup_$currentDateTime.json"
        coroutineScope.launch {
            _notices.emit("Exported to $filePath")
        }
    }

    fun exportToGoogleDrive() {

    }
}
