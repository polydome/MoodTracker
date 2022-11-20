package com.github.polydome.data.ormlite;

import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager instance;
    private Connection connection;

    private ConnectionManager() throws SQLException {
        connection = new Connection("./data.db");
    }

    public static ConnectionManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionManager();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
