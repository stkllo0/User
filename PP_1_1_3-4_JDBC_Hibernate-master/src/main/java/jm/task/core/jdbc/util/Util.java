package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String LOGIN = "root";
    private static final String PASS = "root123.";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, LOGIN, PASS);
        } catch (SQLException ex) {
            System.out.println("Соединение не создано");
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
