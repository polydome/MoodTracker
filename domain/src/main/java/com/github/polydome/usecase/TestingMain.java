package com.github.polydome.usecase;

import java.sql.SQLException;

import com.github.polydome.data.ormlite.Connection;
import com.github.polydome.data.ormlite.ConnectionManager;

public class TestingMain {
    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
    }
}
