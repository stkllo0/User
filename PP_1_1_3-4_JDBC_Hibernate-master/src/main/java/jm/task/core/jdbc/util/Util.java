package jm.task.core.jdbc.util;

import java.sql.*;


public class Util {
    private static final String PASSWORD = "root123.";
    private static final String USER_NAME = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static Connection connection = null;
    private static Util instance = null;
    private Util() {

        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            System.out.println("Драйвер подключен.");
        } catch (SQLException e) {
            System.out.println("error");
        }
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение установлено.");
            }
            Statement statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Отсутствует соединение или некорректно создан statement");
            e.printStackTrace();
        }
    }
    public static Util getInstance() {
        if (null == instance) {
            instance = new Util();
        }
        return instance;
    }
    public static Connection getConnection(){
        return connection;
    }
}
