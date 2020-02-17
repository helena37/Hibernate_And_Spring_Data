package com.imports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public final class MyValues {
    public static final String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    public static final String DATABASE_NAME = "minions_db";

    public static Connection connection;
    public static String query;
    public static PreparedStatement statement;

    public static void connectionProperties() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "*********");

        connection = DriverManager.getConnection(
                CONNECTION_STRING + DATABASE_NAME, properties);
    }
}
