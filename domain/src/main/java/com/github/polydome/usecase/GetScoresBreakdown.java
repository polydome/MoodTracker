package com.github.polydome.usecase;

import com.github.polydome.data.MoodRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GetScoresBreakdown {
    private final @NotNull MoodRepository moodRepository;

    public GetScoresBreakdown(@NotNull MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    /**
     * Retrieves aggregated score for each applicable day of a given month.
     * <p>If no mood has been reported for a given day, the resulting map
     * does not contain such a day as a key.</p>
     *
     * @param year A valid year
     * @param month A valid month
     * @return A map of day index to score. Days are indexed from 0.
     * @throws IllegalArgumentException when either <code>year</code> or <code>month</code> is invalid.
     */
    public @NotNull Map<@NotNull Integer, @NotNull Integer> execute(int year, int month) throws IllegalArgumentException {
        throw new InternalError("Not implemented");
    }
}
