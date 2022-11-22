package com.github.polydome.data.ormlite.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "mood_emotion")
public class MoodEmotionEntity {
    @DatabaseField(foreign = true, foreignAutoRefresh = true,canBeNull = false)
    private MoodEntity mood;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private EmotionEntity emotion;

    public MoodEntity getMood() {
        return mood;
    }

    public void setMood(MoodEntity mood) {
        this.mood = mood;
    }

    public EmotionEntity getEmotion() {
        return emotion;
    }

    public void setEmotion(EmotionEntity emotion) {
        this.emotion = emotion;
    }
}
