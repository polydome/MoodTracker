package com.github.polydome.ui.settings

import com.github.polydome.backup.BackupService
import com.github.polydome.data.MoodRepository
import com.github.polydome.usecase.PerformBackup

abstract class BackupFactory(private val moodRepository: MoodRepository) {
    fun create(filePath: String): PerformBackup = PerformBackup(
        createBackupService(filePath), moodRepository
    )

    protected abstract fun createBackupService(filePath: String): BackupService
}