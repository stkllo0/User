package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        try(Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User" + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)");
        } catch (SQLException e) {
            System.out.println("Таблица не создана");
            e.printStackTrace();
        }
    }
    @Override
    public void dropUsersTable() {
        try(Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS User");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу");
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement("INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("При добавлении пользователя произошла ошибка");
            e.printStackTrace();
        }
    }
    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement("DELETE FROM User WHERE ID = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось удалить пользователя");
            e.printStackTrace();
        }

    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(PreparedStatement statement = Util.getConnection().prepareStatement("SELECT * FROM User")) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                User user = new User(resultSet.getString("Name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Не удалось получить список");
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        try(PreparedStatement statement = Util.getConnection().prepareStatement("DELETE FROM User")) {
            statement.execute();
        } catch(SQLException e) {
            System.out.println("Не удалось очистить таблицу");
            e.printStackTrace();
        }
    }
}
