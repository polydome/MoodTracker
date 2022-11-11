package com.github.polydome.usecase;

import com.github.polydome.data.MoodRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ReportMood {
    private final @NotNull MoodRepository moodRepository;

    public ReportMood(@NotNull MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    /**
     * Submits a mood report with a current datetime.
     * @param score Mood score
     * @param emotions A set of selected emotions
     * @param note Optional note
     */
    public void reportCurrentMood(int score,
                           @NotNull Set<String> emotions,
                           @Nullable String note) {
        throw new InternalError("Not implemented");
    }

    /**
     * Retrieves all emotions that are known to the system
     * @return A list of emotions.
     */
    @NotNull
    public Set<String> getKnownEmotions() {
        throw new InternalError("Not implemented");
    }
}
