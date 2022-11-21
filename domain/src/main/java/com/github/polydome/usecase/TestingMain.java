package com.github.polydome.usecase;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.polydome.data.MoodRepositorySqlite;
import com.github.polydome.model.Emotion;
import com.github.polydome.model.Mood;

public class TestingMain {
    public static void main(String[] args) throws SQLException {
        List<Emotion> emotions = new ArrayList<>();
        emotions.add(new Emotion(0, "Mleko"));
        emotions.add(new Emotion(0, "Maslo"));
        emotions.add(new Emotion(0, "Chleb"));

        Mood mood = new Mood(0, 5, emotions, "Brak");
        MoodRepositorySqlite repo = new MoodRepositorySqlite();
        repo.insert(LocalDateTime.now(), mood);
    }
}
