package com.github.polydome.data;

import com.github.polydome.model.Emotion;
import com.github.polydome.model.Mood;
import com.github.polydome.model.MoodEntry;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface MoodRepository {
    /**
     * Reports a mood at a given datetime.
     * For mood with an id = 0, a new generated ID will be assigned.
     *
     * @param dateTime A datetime that the mood will be reported at
     * @param mood A mood to be reported
     */
    void insert(LocalDateTime dateTime, Mood mood);

    /**
     * Retrieves all mood scores that were reported within a period.
     *
     * @param periodStart The latest point in time to be considered
     * @param periodEnd The earliest point in time to be considered
     * @return A list of applicable entries
     */
    List<MoodEntry> findEntriesReportedWithinPeriod(LocalDateTime periodStart, LocalDateTime periodEnd);

    /**
     * Inserts all entries contained in passed list to database.
     * @param entries List of entries to be inserted
     */
    void merge(List<MoodEntry> entries);

    /**
     * Retrieves all mood entries that were ever inserted.
     * @return List of mood entries
     */
    List<MoodEntry> findAllEntries();

    /**
     * Retrieves all known emotions
     * @return List of emotions
     * @throws SQLException
     */
    List<Emotion> findReportedEmotions() throws SQLException;
}
