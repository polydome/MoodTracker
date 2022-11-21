package com.github.polydome.usecase;

import java.sql.SQLException;

import com.github.polydome.data.MoodRepositorySqlite;

public class TestingMain {
    public static void main(String[] args) throws SQLException {
        MoodRepositorySqlite repo = new MoodRepositorySqlite();
        var entries = repo.findAllEntries();
    }
}
