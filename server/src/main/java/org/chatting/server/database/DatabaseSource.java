package org.chatting.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSource {
    private static final String DATABASE_PATH = "/home/catalin/Documents/master/projects/aspects/chatting/database/server.db";

    public static Connection createConnection() {
        final String url = String.format("jdbc:sqlite:%s", DATABASE_PATH);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;

    }
}
