package com.github.polydome.data.ormlite.entity;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "mood_entries")
public class MoodEntryEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private Date dateTime; 

    @DatabaseField(canBeNull = false, foreign = true)
    private MoodEntity mood;

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
}
