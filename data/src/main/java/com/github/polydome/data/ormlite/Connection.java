package com.github.polydome.data.ormlite;

import java.sql.SQLException;

import com.github.polydome.data.ormlite.entity.EmotionEntity;
import com.github.polydome.data.ormlite.entity.MoodEmotionEntity;
import com.github.polydome.data.ormlite.entity.MoodEntity;
import com.github.polydome.data.ormlite.entity.MoodEntryEntity;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Connection {
    private JdbcPooledConnectionSource connectionSource;

    public Connection(String url) throws SQLException {
        connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:" + url);
        initDb();
    }

    public JdbcPooledConnectionSource getConnectionSource() {
        return connectionSource;
    }

    private void initDb() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, EmotionEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, MoodEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, MoodEntryEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, MoodEmotionEntity.class);
    }
}
