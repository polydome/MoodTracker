package com.github.polydome.usecase;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import com.github.polydome.data.MoodRepositorySqlite;
import com.github.polydome.model.Emotion;
import com.github.polydome.model.Mood;
import com.github.polydome.model.MoodEntry;

public class TestingMain {
    public static void main(String[] args) throws SQLException {
        MoodRepositorySqlite repo = new MoodRepositorySqlite();
        var entries = repo.findEntriesReportedWithinPeriod(LocalDateTime.of(2000, Month.FEBRUARY, 2, 6, 30), LocalDateTime.now());
        var entry1 = new MoodEntry(0,
            LocalDateTime.now(),
            new Mood(0,
                3,
                Arrays.asList(
                    new Emotion(0, "pain"),
                    new Emotion(0, "baldness")
                ),
                "Test"
            )
        );
        var entry2 = new MoodEntry(0,
            LocalDateTime.now(),
            new Mood(0,
                3,
                Arrays.asList(
                    new Emotion(0, "migraine"),
                    new Emotion(0, "test2")
                ),
                "Test325"
            )
        );

        var entries2 = Arrays.asList(entry1, entry2);
        repo.merge(entries2);

    }
}
