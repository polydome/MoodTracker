package com.github.polydome.usecase;

import com.github.polydome.backup.BackupService;
import com.github.polydome.data.MoodRepository;
import com.github.polydome.model.MoodEntry;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public class RestoreBackup {
    private final @NotNull BackupService backupService;
    private final @NotNull MoodRepository moodRepository;

    public RestoreBackup(@NotNull BackupService backupService, @NotNull MoodRepository moodRepository) {
        this.backupService = backupService;
        this.moodRepository = moodRepository;
    }

    /**
     * Restores data from a service passed as the constructor parameter.
     * The retrieved data is merged onto the existing data.
     */
    public void execute() {
        //backup read, na bazie odpalić merge
        List<MoodEntry> backupEntries = backupService.read();
        moodRepository.merge(backupEntries);
    }
}
