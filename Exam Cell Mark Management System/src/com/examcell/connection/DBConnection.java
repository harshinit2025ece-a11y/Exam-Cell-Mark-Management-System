package com.examcell.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/exam_cell_db";

    private static final String USER = "root";

    private static final String PASSWORD = "harshu1725";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL, USER, PASSWORD
        );
    }

    public static void testConnection() {

        try (Connection con = getConnection()) {

            System.out.println("MySQL Connected Successfully!");

        } catch (SQLException e) {

            System.out.println("Connection Failed!");
            System.out.println(e.getMessage());
        }
    }
}
