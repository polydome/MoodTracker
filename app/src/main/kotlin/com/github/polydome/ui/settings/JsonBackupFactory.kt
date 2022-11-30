package com.github.polydome.ui.settings

import JsonBackupService
import com.github.polydome.backup.BackupService
import com.github.polydome.data.MoodRepository

class JsonBackupFactory(moodRepository: MoodRepository) : BackupFactory(
    moodRepository
) {
    override fun createBackupService(filePath: String): BackupService =
        JsonBackupService(filePath)
}