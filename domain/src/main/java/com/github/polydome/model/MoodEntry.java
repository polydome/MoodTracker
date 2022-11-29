package com.github.polydome.model;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
@JsonDeserialize(builder = MoodEntry.MoodEntryBuilder.class)
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

    @JsonPOJOBuilder(withPrefix = "")
    public static class MoodEntryBuilder {

    }
}
