package com.github.polydome.ui.settings

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _notices = MutableSharedFlow<String>()
    val notices: Flow<String> = _notices

    fun exportToJson() {
        coroutineScope.launch {
            _notices.emit("Exported to JSON")
        }
    }

    fun exportToCsv() {

    }

    fun exportToGoogleDrive() {

    }
}
