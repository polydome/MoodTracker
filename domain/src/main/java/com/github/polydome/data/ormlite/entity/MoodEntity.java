package com.github.polydome.data.ormlite.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "moods")
public class MoodEntity {
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(canBeNull = false)
    private int score;

    @DatabaseField
    private String note;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<MoodEmotionEntity> moodEmotions;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<MoodEntryEntity> moodEntries;

    public ForeignCollection<MoodEmotionEntity> getMoodEmotions() {
        return moodEmotions;
    }

    public void setMoodEmotions(ForeignCollection<MoodEmotionEntity> moodEmotions) {
        this.moodEmotions = moodEmotions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
