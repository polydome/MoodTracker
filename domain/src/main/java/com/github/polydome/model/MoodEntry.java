package com.github.polydome.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class MoodEntry {
    private final int id;
    @NotNull private final LocalDateTime dateTime;
    @NotNull private final Mood mood;

    public MoodEntry(int id, @NotNull LocalDateTime dateTime, @NotNull Mood mood) {
        this.id = id;
        this.dateTime = dateTime;
        this.mood = mood;
    }

    public @NotNull LocalDateTime getDateTime() {
        return dateTime;
    }

    public @NotNull Mood getMood() {
        return mood;
    }

    public int getId() {
        return id;
    }
}
