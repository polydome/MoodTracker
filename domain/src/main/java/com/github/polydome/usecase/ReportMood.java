package com.github.polydome.usecase;

import com.github.polydome.data.MoodRepository;
import com.github.polydome.model.Emotion;
import com.github.polydome.model.Mood;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ReportMood {
    private final @NotNull MoodRepository moodRepository;

    public ReportMood(@NotNull MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    /**
     * Submits a mood report with a current datetime.
     * 
     * @param score    Mood score
     * @param emotions A set of selected emotions
     * @param note     Optional note
     */
    public void reportCurrentMood(int score,
            @NotNull Set<String> emotions,
            @Nullable String note) {

        List<Emotion> emotionsList = new ArrayList<Emotion>();
        emotions.forEach(emotion -> {
            emotionsList.add(new Emotion(0, emotion));
        });
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        Mood mood = new Mood(0, score, emotionsList, note);
        moodRepository.insert(currentTimeStamp, mood);
    }

    /**
     * Retrieves all emotions that are known to the system
     * 
     * @return A list of emotions.
     */
    @NotNull
    public Set<String> getKnownEmotions() {
        Set<String> emotions = Collections.<String>emptySet();
        List<Emotion> emotionList = moodRepository.findReportedEmotions();
        emotionList.forEach(emotion -> {
            emotions.add(emotion.getName());
        });
        return emotions;
    }
}
