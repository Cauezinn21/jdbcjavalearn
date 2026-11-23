package com.caue.jdbcjava.factory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/jdbcjava";
        String username = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, username, password);
    }
}
