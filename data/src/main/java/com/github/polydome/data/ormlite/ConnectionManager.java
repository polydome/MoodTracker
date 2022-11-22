package com.github.polydome.data.ormlite;

import java.sql.SQLException;

/**
 * Singleton class holding connection
 */
public class ConnectionManager {
    private static ConnectionManager instance;
    private Connection connection;

    private ConnectionManager() throws SQLException {
        connection = new Connection("./data.db");
    }

    /**
     * Accessor method
     * @return instance of a class
     * @throws SQLException
     */
    public static ConnectionManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionManager();
        }

        return instance;
    }

    /**
     * Method that returns connection to database
     * @return connection
     */
    public Connection getConnection() {
        return connection;
    }
}
