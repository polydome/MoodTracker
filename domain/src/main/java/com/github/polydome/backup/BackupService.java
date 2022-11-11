package com.github.polydome.backup;

import com.github.polydome.model.MoodEntry;

import java.util.List;

public interface BackupService {
    /**
     * Stores given entries as a backup
     * @param entries Mood entries to be backed up
     */
    void write(List<MoodEntry> entries);

    /**
     * Retrieves all entries stored as a backup
     * @return A list of backed up entries
     */
    List<MoodEntry> read();
}
