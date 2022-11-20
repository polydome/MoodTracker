package com.github.polydome.data;

import java.time.LocalDateTime;
import java.util.List;

import com.github.polydome.model.Emotion;
import com.github.polydome.model.Mood;
import com.github.polydome.model.MoodEntry;

public class MoodRepositorySqlite implements MoodRepository {

    @Override
    public void insert(LocalDateTime dateTime, Mood mood) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<MoodEntry> findEntriesReportedWithinPeriod(LocalDateTime periodStart, LocalDateTime periodEnd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void merge(List<MoodEntry> entries) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<MoodEntry> findAllEntries() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Emotion> findReportedEmotions() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
