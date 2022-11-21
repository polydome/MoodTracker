package com.github.polydome.data;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.polydome.data.ormlite.Connection;
import com.github.polydome.data.ormlite.ConnectionManager;
import com.github.polydome.data.ormlite.entity.EmotionEntity;
import com.github.polydome.data.ormlite.entity.MoodEmotionEntity;
import com.github.polydome.data.ormlite.entity.MoodEntity;
import com.github.polydome.data.ormlite.entity.MoodEntryEntity;
import com.github.polydome.data.util.DateUtil;
import com.github.polydome.model.Emotion;
import com.github.polydome.model.Mood;
import com.github.polydome.model.MoodEntry;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;

public class MoodRepositorySqlite implements MoodRepository {
    private Connection connection;

    public MoodRepositorySqlite() throws SQLException {
        connection = ConnectionManager.getInstance().getConnection();
    }

    @Override
    public void insert(LocalDateTime dateTime, Mood mood) throws SQLException {
        Date date = DateUtil.fromLocalDateTime(dateTime);

        MoodEntity moodEntity = saveMood(mood);
        createMoodEntry(moodEntity, date);
    }

    private MoodEntity saveMood(Mood mood) throws SQLException {
        MoodEntity moodEntity = new MoodEntity();
        moodEntity.setId(mood.getId()); // If id == 0, create new record
        moodEntity.setNote(mood.getNote());
        moodEntity.setScore(mood.getScore());
        Dao<MoodEntity, Integer> moodEntityDao = DaoManager.createDao(connection.getConnectionSource(), MoodEntity.class);
        moodEntityDao.createOrUpdate(moodEntity);
        addEmotionsToMoodEntity(moodEntity, mood.getEmotions());

        return moodEntity;
    }

    private void addEmotionsToMoodEntity(MoodEntity moodEntity, List<Emotion> emotions) throws SQLException {
        Dao<EmotionEntity, Integer> emotionEntityDao = DaoManager.createDao(connection.getConnectionSource(), EmotionEntity.class);
        Dao<MoodEmotionEntity, Integer> moodEmotionEntitydDao = DaoManager.createDao(connection.getConnectionSource(), MoodEmotionEntity.class);

        insertNewEmotions(emotions);
        List<EmotionEntity> knownEmotionEntities = emotionEntityDao.queryForAll();

        // Delete all relationships with moodEntity
        DeleteBuilder<MoodEmotionEntity, Integer> deleteBuilder = moodEmotionEntitydDao.deleteBuilder();
        deleteBuilder.where().eq("mood_id", moodEntity.getId());
        deleteBuilder.delete();

        List<MoodEmotionEntity> moodEmotionEntities = new ArrayList<>();
        emotions.forEach(emotion -> {
            // Get emotion entity with that name
            EmotionEntity emotionEntity = knownEmotionEntities.stream().filter(knownEmotionEntity -> knownEmotionEntity.getName().equals(emotion.getName())).findAny().get();
            if (emotionEntity == null) { // Should not ever happen, but still - for safety
                return;
            }
            MoodEmotionEntity moodEmotionEntity = new MoodEmotionEntity();
            moodEmotionEntity.setEmotion(emotionEntity);
            moodEmotionEntity.setMood(moodEntity);
            moodEmotionEntities.add(moodEmotionEntity);
        });

        moodEmotionEntitydDao.create(moodEmotionEntities);
    }

    private MoodEntryEntity createMoodEntry(MoodEntity moodEntity, Date date) throws SQLException {
        MoodEntryEntity moodEntryEntity = new MoodEntryEntity();
        moodEntryEntity.setMood(moodEntity);
        moodEntryEntity.setDateTime(date);
        Dao<MoodEntryEntity, Integer> moodEntryEntityDao = DaoManager.createDao(connection.getConnectionSource(), MoodEntryEntity.class);
        moodEntryEntityDao.create(moodEntryEntity);

        return moodEntryEntity;
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
        emotions = new ArrayList<>(emotions); // Copy list, so original reference remians unchanged

        Dao<EmotionEntity, Integer> dao = DaoManager.createDao(connection.getConnectionSource(), EmotionEntity.class);

        // Get existing emotion entities
        List<EmotionEntity> knownEmotions = dao.queryForAll();

        // Filter existing emotions
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
