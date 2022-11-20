package com.github.polydome.usecase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.polydome.data.MoodRepositorySqlite;
import com.github.polydome.model.Emotion;

public class TestingMain {
    public static void main(String[] args) throws SQLException {
        MoodRepositorySqlite repo = new MoodRepositorySqlite();
        List<Emotion> emotions = new ArrayList<>();
    }
}
