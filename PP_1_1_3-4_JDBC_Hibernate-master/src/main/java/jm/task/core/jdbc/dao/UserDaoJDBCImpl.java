package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.swing.text.html.HTMLDocument;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getInstance().getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User" + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)");
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e){
            System.out.println("Таблица не создана");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS User");
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e){
            System.out.println("Не удалось удалить таблицу");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)")) {
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("При добавлении пользователя произошла ошибка");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id){
        try (PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM User WHERE ID = ?")) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить пользователя");
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try( PreparedStatement statement = connection.prepareStatement("SELECT * FROM User")){
            connection.setAutoCommit(false);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                User user = new User(resultSet.getString("Name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e){
            System.out.println("Не удалось получить список");
            e.printStackTrace();
        }
        return users;


    }

    public void cleanUsersTable() {
        try(Connection connection =Util.getInstance().getConnection()){
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE User");
            connection.commit();
            connection.setAutoCommit(true);
        } catch(SQLException e){
            System.out.println("Не удалось очистить таблицу");
            e.printStackTrace();
        }
    }
}
