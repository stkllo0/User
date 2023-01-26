package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.mysql.cj.conf.PropertyKey.DBNAME;
import static com.mysql.cj.conf.PropertyKey.HOST;
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

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        String connectionURL = "jdbc:mysql://" + HOST + ":3306/" + DBNAME;

        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/data?useSSL=false");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "root123.");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

            settings.put(Environment.SHOW_SQL, "true");

            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            settings.put(Environment.HBM2DDL_AUTO, "create-drop");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;

    }

}
