package com.github.polydome.data.ormlite;

import java.sql.SQLException;

import com.github.polydome.data.ormlite.entity.EmotionEntity;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Connection {
    private JdbcPooledConnectionSource connectionSource;

    public Connection(String url) throws SQLException {
        connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:" + url);
        initDb();
    }

    public JdbcPooledConnectionSource getConnection() {
        return connectionSource;
    }

    private void initDb() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, EmotionEntity.class);
    }
}
