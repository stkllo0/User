package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Катя", "Тузова", (byte) 21);
        userService.saveUser("Лиза", "Жильцова", (byte) 21);
        userService.saveUser("Женя", "Краснов", (byte) 44);
        userService.saveUser("Александра", "Сазонова", (byte) 19);

        userService.removeUserById(3);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
