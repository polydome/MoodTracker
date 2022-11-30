package com.github.polydome.ui.settings

import com.github.polydome.backup.BackupService
import com.github.polydome.data.MoodRepository
import com.github.polydome.usecase.PerformBackup
import com.github.polydome.usecase.RestoreBackup

abstract class BackupFactory(private val moodRepository: MoodRepository) {
    fun createPerformBackup(filePath: String): PerformBackup = PerformBackup(
        createBackupService(filePath), moodRepository
    )

    fun createRestoreBackup(filePath: String): RestoreBackup = RestoreBackup(
        createBackupService(filePath), moodRepository
    )

    protected abstract fun createBackupService(filePath: String): BackupService
}