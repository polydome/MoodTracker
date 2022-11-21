package com.github.polydome.usecase;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;

import com.github.polydome.data.MoodRepositorySqlite;

public class TestingMain {
    public static void main(String[] args) throws SQLException {
        MoodRepositorySqlite repo = new MoodRepositorySqlite();
        var entries = repo.findEntriesReportedWithinPeriod(LocalDateTime.of(2000, Month.FEBRUARY, 2, 6, 30), LocalDateTime.now());
    }
}
