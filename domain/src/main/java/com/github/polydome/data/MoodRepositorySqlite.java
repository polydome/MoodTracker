package com.github.polydome.data;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.polydome.data.ormlite.Connection;
import com.github.polydome.data.ormlite.ConnectionManager;
import com.github.polydome.data.ormlite.entity.EmotionEntity;
import com.github.polydome.model.Emotion;
import com.github.polydome.model.Mood;
import com.github.polydome.model.MoodEntry;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class MoodRepositorySqlite implements MoodRepository {
    private Connection connection;

    public MoodRepositorySqlite() throws SQLException {
        connection = ConnectionManager.getInstance().getConnection();
    }

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
    public List<Emotion> findReportedEmotions() throws SQLException {
        List<Emotion> emotions = new ArrayList<Emotion>();

        Dao<EmotionEntity, Integer> dao = DaoManager.createDao(connection.getConnectionSource(), EmotionEntity.class);
        List<EmotionEntity> entities = dao.queryForAll();
        entities.forEach((n) -> {
            emotions.add(new Emotion(n.getId(), n.getName()));
        });

        return emotions;
    }

    /**
     * Inserts new emotions from list of all emotions
     * @throws SQLException
     */
    private void insertNewEmotions(List<Emotion> emotions) throws SQLException {
        Dao<EmotionEntity, Integer> dao = DaoManager.createDao(connection.getConnectionSource(), EmotionEntity.class);

        // Get existing emotion entities
        List<EmotionEntity> knownEmotions = dao.queryForAll();

        // Filter existing emotions
        // emotions.removeIf(emotion -> knownEmotions.stream().anyMatch(knownEmotion -> knownEmotion.getName() == emotion.getName()));
        emotions.removeIf(emotion -> knownEmotions.stream().anyMatch(knownEmotion -> knownEmotion.getName().equals(emotion.getName())));

        // Create list of entities to add
        List<EmotionEntity> emotionEntitiesToAdd = new ArrayList<EmotionEntity>();
        emotions.forEach(emotion -> {
            EmotionEntity emotionEntity = new EmotionEntity();
            emotionEntity.setName(emotion.getName());
            emotionEntitiesToAdd.add(emotionEntity);
        });

        // Add entities
        dao.create(emotionEntitiesToAdd);
    }
    
}
