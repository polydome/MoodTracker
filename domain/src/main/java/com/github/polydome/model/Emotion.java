package com.github.polydome.model;

import org.jetbrains.annotations.NotNull;

public class Emotion {
    private final int id;
    @NotNull
    private final String name;

    public Emotion(int id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    public @NotNull String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
