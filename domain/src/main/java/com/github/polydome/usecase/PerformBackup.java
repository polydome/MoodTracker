package com.github.polydome.usecase;

import com.github.polydome.backup.BackupService;
import com.github.polydome.data.MoodRepository;
import org.jetbrains.annotations.NotNull;

public class PerformBackup {
    private final @NotNull BackupService backupService;
    private final @NotNull MoodRepository moodRepository;

    public PerformBackup(@NotNull BackupService backupService, @NotNull MoodRepository moodRepository) {
        this.backupService = backupService;
        this.moodRepository = moodRepository;
    }

    /**
     * Performs a complete mood storage backup using a service passed as
     * the constructor parameter
     */
    public void execute() {
        throw new InternalError("Not implemented");
    }
}
