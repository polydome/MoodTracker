package com.github.polydome.data.ormlite.entity;

import java.util.Date;

import com.github.polydome.data.util.DateUtil;
import com.github.polydome.model.MoodEntry;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Entity class of MoodEntry
 */
@DatabaseTable(tableName = "mood_entries")
public class MoodEntryEntity {
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(canBeNull = false)
    private Date dateTime; 

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private MoodEntity mood;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MoodEntity getMood() {
        return mood;
    }

    public void setMood(MoodEntity mood) {
        this.mood = mood;
    }

    /**
     * Translated entity to model
     * @return model MoodEntry
     */
    public MoodEntry toMoodEntry() {
        return new MoodEntry(getId(), DateUtil.fromDate(getDateTime()), getMood().toMood());
    }
}
