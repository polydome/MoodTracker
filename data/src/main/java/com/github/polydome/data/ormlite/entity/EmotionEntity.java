package com.github.polydome.data.ormlite.entity;

import com.github.polydome.model.Emotion;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Entity class of Emotion
 */
@DatabaseTable(tableName = "emotions")
public class EmotionEntity {
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id; 

    @DatabaseField(canBeNull = false)
    private String name;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<MoodEmotionEntity> moodEmotions;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Translates entity to model
     * @return Emotion model
     */
    public Emotion toEmotion() {
        return new Emotion(getId(), getName());
    }

}
