package com.github.polydome.ui.settings

import GoogleDriveBackupService
import com.github.polydome.backup.BackupService
import com.github.polydome.data.MoodRepository

class GoogleDriveBackupFactory(moodRepository: MoodRepository) : BackupFactory(moodRepository) {
    override fun createBackupService(filePath: String): BackupService = GoogleDriveBackupService(filePath)
}