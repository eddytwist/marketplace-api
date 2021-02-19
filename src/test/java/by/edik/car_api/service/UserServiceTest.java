package by.edik.car_api.service;

import by.edik.car_api.db.DataSource;
import by.edik.car_api.model.User;
import by.edik.car_api.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final String USERNAME = "user";
    private static final String EMAIL = "user@user.com";
    private static final String PASSWORD = "user";

    UserServiceImpl userService = UserServiceImpl.getInstance();
    User user;

    @BeforeEach
    void setUp() {
        user = userService.create(new User()
                .setUsername(USERNAME)
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
        );
    }

    @AfterEach
    void tearDown() {
        truncateAll();
    }

    @Test
    void create() {
        User createdUser = userService.getById(user.getUserId());
        assertTrue(createdUser.getUserId() > 0);
        assertEquals(USERNAME, createdUser.getUsername());
        assertEquals(EMAIL, createdUser.getEmail());
        assertEquals(PASSWORD, createdUser.getPassword());
    }

    @Test
    void getById() {
        User foundedUser = userService.getById(user.getUserId());
        assertTrue(foundedUser.getUserId() > 0);
        assertEquals(USERNAME, foundedUser.getUsername());
        assertEquals(EMAIL, foundedUser.getEmail());
        assertEquals(PASSWORD, foundedUser.getPassword());
    }

    @Test
    void getAll() {
        userService.create(user
                .setUsername("Fedor")
                .setEmail("fedor@mail.com")
                .setPassword("pass")
        );
        List<User> foundedUsers = userService.getAll();
        assertEquals(foundedUsers.size(), 2);

        User firstUser = foundedUsers.get(0);
        assertTrue(firstUser.getUserId() > 0);
        assertEquals(USERNAME, firstUser.getUsername());
        assertEquals(EMAIL, firstUser.getEmail());
        assertEquals(PASSWORD, firstUser.getPassword());

        User secondUser = foundedUsers.get(1);
        assertTrue(secondUser.getUserId() > 0);
        assertEquals("Fedor", secondUser.getUsername());
        assertEquals("fedor@mail.com", secondUser.getEmail());
        assertEquals("pass", secondUser.getPassword());
    }

    @Test
    void update() {
        User foundedUser = userService.getById(user.getUserId())
                .setUsername("newUser")
                .setEmail("newuser@user.com")
                .setPassword("new_password");
        userService.update(foundedUser);
        User updatedUser = userService.getById(foundedUser.getUserId());
        assertTrue(updatedUser.getUserId() > 0);
        assertEquals("newUser", updatedUser.getUsername());
        assertEquals("newuser@user.com", updatedUser.getEmail());
        assertEquals("new_password", updatedUser.getPassword());
    }

    @Test
    void delete() {
        userService.delete(user.getUserId());
        assertNull(userService.getById(user.getUserId()));
    }

    @SneakyThrows
    private void truncateAll() {
        Connection connection = DataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "truncate pictures, ads, user_information, user_phones, users;"
        );
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}