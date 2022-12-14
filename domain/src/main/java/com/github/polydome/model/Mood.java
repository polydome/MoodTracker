package com.github.polydome.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;

import java.util.List;

@Builder
@JsonDeserialize(builder = Mood.MoodBuilder.class)
public class Mood {
    private final int id;
    private final int score;
    @NotNull
    private final List<Emotion> emotions;
    @Nullable
    private final String note;

    public Mood(int id, int score, @NotNull List<Emotion> emotions, @Nullable String note) {
        this.id = id;
        this.score = score;
        this.emotions = emotions;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public @NotNull List<Emotion> getEmotions() {
        return emotions;
    }

    public @Nullable String getNote() {
        return note;
    }
    
    @JsonPOJOBuilder(withPrefix = "")
    public static class MoodBuilder {

    }
}
