package com.github.polydome.ui.settings

import com.github.polydome.ui.event.DataEvent
import com.github.polydome.ui.event.Sink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SettingsViewModel(
    private val promptDirectory: () -> File,
    private val promptFile: () -> File,
    private val jsonBackupFactory: BackupFactory,
    private val dataEventSink: Sink<DataEvent>
) {
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
            showNotice("Exporting...")

            jsonBackupFactory
                .createPerformBackup(filePath)
                .execute()

            showNotice("Exported to $filePath")
        }
    }

    fun importFromJson() {
        val file = promptFile()

        coroutineScope.launch {
            showNotice("Importing...")

            jsonBackupFactory
                .createRestoreBackup(file.path)
                .execute()

            dataEventSink.emit(DataEvent.UPDATED)

            showNotice("Imported from ${file.path}")
        }
    }

    fun exportToGoogleDrive() {

    }

    private fun showNotice(message: String) {
        coroutineScope.launch {
            _notices.emit(message)
        }
    }
}

