jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS User" + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось создать таблицу");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS User";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            transaction.commit();
        } catch (Exception e) {

            System.out.println("Не удалось удалить таблицу");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Не удалось добавить пользователя");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Не удалось удалить пользователя");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Не удалось получить пользователей");
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Не удалось очистить таблицу");
            e.printStackTrace();
        }
    }
}
