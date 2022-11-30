package com.github.polydome.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun SettingsButton(modifier: Modifier = Modifier, settingsViewModel: SettingsViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                expanded = !expanded
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            SettingsItem(
                title = "Export to JSON",
                onClick = {
                    expanded = false
                    settingsViewModel.exportToJson()
                }
            )

            SettingsItem(
                title = "Import from JSON",
                onClick = {
                    expanded = false
                    settingsViewModel.importFromJson()
                }
            )

            SettingsItem(
                title = "Export to Google Drive",
                onClick = {
                    expanded = false
                    settingsViewModel.exportToGoogleDrive()
                }
            )

            SettingsItem(
                title = "Import from GoogleDrive",
                onClick = {
                    expanded = false
                    settingsViewModel.importFromGoogleDrive()
                }
            )
        }
    }
}

@Composable
fun SettingsItem(onClick: () -> Unit, title: String) {
    DropdownMenuItem(
        onClick = onClick
    ) {
        Text(title)
    }
}