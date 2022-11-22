package com.github.polydome.data;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.github.polydome.data.exception.SQLRuntimeException;
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

    public MoodRepositorySqlite() {
        try {
            connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLRuntimeException("Couldn't initialize database connection", e);
        }
    }

    @Override
    public void insert(LocalDateTime dateTime, Mood mood) {
        try {
            Date date = DateUtil.fromLocalDateTime(dateTime);

            MoodEntity moodEntity = saveMood(mood);
            createMoodEntryEntity(moodEntity, date);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLRuntimeException("Couldn't insert mood into database", e);
        }
    }

    /**
     * Creates MoodEntity object, then perists it in database
     * @param mood
     * @return Persisted object
     * @throws SQLException
     */
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

    /**
     * Adds EmotionEntity objects based of list of Emotion, and then creates realtionship between those and EmotionEntity
     * @param moodEntity
     * @param emotions
     * @throws SQLException
     */
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

    /**
     * Persists MoodEntity object into database, with all of its relations
     * @param moodEntity - Entity of Mood model
     * @param date - Date, normalized
     * @return
     * @throws SQLException
     */
    private MoodEntryEntity createMoodEntryEntity(MoodEntity moodEntity, Date date) throws SQLException {
        MoodEntryEntity moodEntryEntity = new MoodEntryEntity();
        moodEntryEntity.setMood(moodEntity);
        moodEntryEntity.setDateTime(date);
        Dao<MoodEntryEntity, Integer> moodEntryEntityDao = DaoManager.createDao(connection.getConnectionSource(), MoodEntryEntity.class);
        moodEntryEntityDao.create(moodEntryEntity);

        return moodEntryEntity;
    }

    /**
     * Saves MoodEntry object into database
     * @param moodEntry - model MoodEntry
     * @return - encja obiektu MoodEntry
     * @throws SQLException
     */
    private MoodEntryEntity saveMoodEntry(MoodEntry moodEntry) throws SQLException {
        // Handle emotions
        @NotNull List<Emotion> emotions = moodEntry.getMood().getEmotions();
        insertNewEmotions(emotions);

        // Handle mood
        @NotNull Mood mood = moodEntry.getMood();
        MoodEntity moodEntity = saveMood(mood);

        // Handle mood entry
        MoodEntryEntity moodEntryEntity = new MoodEntryEntity();
        moodEntryEntity.setDateTime(DateUtil.fromLocalDateTime(moodEntry.getDateTime()));
        moodEntryEntity.setId(moodEntry.getId());
        moodEntryEntity.setMood(moodEntity);
        var moodEntryEntityDao = DaoManager.createDao(connection.getConnectionSource(), MoodEntryEntity.class);
        moodEntryEntityDao.createOrUpdate(moodEntryEntity);

        return moodEntryEntity;
    }

    @Override
    public List<MoodEntry> findEntriesReportedWithinPeriod(LocalDateTime periodStart, LocalDateTime periodEnd) {
        try {
            Dao<MoodEntryEntity, Integer> moodEntryEntityDao = DaoManager.createDao(connection.getConnectionSource(), MoodEntryEntity.class);

            var query = moodEntryEntityDao.queryBuilder();
            query.where().gt("dateTime", DateUtil.fromLocalDateTime(periodStart)).and().lt("dateTime", DateUtil.fromLocalDateTime(periodEnd));

            List<MoodEntry> moodEntries = new ArrayList<>();
            List<MoodEntryEntity> moodEntryEntities = query.query();
            moodEntryEntities.forEach(moodEntryEntity -> {
                moodEntries.add(moodEntryEntity.toMoodEntry());
            });

            return moodEntries;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLRuntimeException("Couldn't resolve find query", e);
        }
    }

    @Override
    public void merge(List<MoodEntry> entries) {
        try {
            for (MoodEntry entry : entries) {
                saveMoodEntry(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLRuntimeException("Couldn't merge records", e);
        }
    }

    @Override
    public List<MoodEntry> findAllEntries() {
        try {
            Dao<MoodEntryEntity, Integer> moodEntryEntityDao = DaoManager.createDao(connection.getConnectionSource(), MoodEntryEntity.class);

            List<MoodEntry> moodEntries = new ArrayList<>();
            List<MoodEntryEntity> moodEntryEntities = moodEntryEntityDao.queryForAll();
            moodEntryEntities.forEach(moodEntryEntity -> {
                moodEntries.add(moodEntryEntity.toMoodEntry());
            });

            return moodEntries;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLRuntimeException("Couldn't resolve find query", e);
        }

    }

    @Override
    public List<Emotion> findReportedEmotions() {
        try {
            List<Emotion> emotions = new ArrayList<Emotion>();

            Dao<EmotionEntity, Integer> dao = DaoManager.createDao(connection.getConnectionSource(), EmotionEntity.class);
            List<EmotionEntity> entities = dao.queryForAll();
            entities.forEach((n) -> {
                emotions.add(new Emotion(n.getId(), n.getName()));
            });

            return emotions;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLRuntimeException("Couldn't resolve find query", e);
        }
    }

    /**
     * Inserts new emotions from list of all emotions, doesn't add emotions that already exists, that is have the same name
     * @param emotions - List of Emotion objects to insert
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
