package com.edik.car.api.dao;

import com.edik.car.api.dao.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDaoTest {

    private static final String USERNAME = "user";
    private static final String EMAIL = "user@user.com";
    private static final String PASSWORD = "user";

    UserDao userDao = UserDao.getInstance();
    User user;

    @BeforeEach
    void setUp() {
        user = userDao.save(new User()
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
        User createdUser = userDao.findById(user.getUserId());
        assertTrue(createdUser.getUserId() > 0);
        assertEquals(USERNAME, createdUser.getUsername());
        assertEquals(EMAIL, createdUser.getEmail());
        assertEquals(PASSWORD, createdUser.getPassword());
    }

    @Test
    void getById() {
        User foundedUser = userDao.findById(user.getUserId());
        assertTrue(foundedUser.getUserId() > 0);
        assertEquals(USERNAME, foundedUser.getUsername());
        assertEquals(EMAIL, foundedUser.getEmail());
        assertEquals(PASSWORD, foundedUser.getPassword());
    }

    @Test
    void getAll() {
        userDao.save(user
            .setUsername("Fedor")
            .setEmail("fedor@mail.com")
            .setPassword("pass")
        );
        List<User> foundedUsers = userDao.findAll();
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
        User foundedUser = userDao.findById(user.getUserId())
            .setUsername("newUser")
            .setEmail("newuser@user.com")
            .setPassword("new_password");
        userDao.update(foundedUser);
        User updatedUser = userDao.findById(foundedUser.getUserId());
        assertTrue(updatedUser.getUserId() > 0);
        assertEquals("newUser", updatedUser.getUsername());
        assertEquals("newuser@user.com", updatedUser.getEmail());
        assertEquals("new_password", updatedUser.getPassword());
    }

    @Test
    void delete() {
        userDao.delete(user.getUserId());
        assertNull(userDao.findById(user.getUserId()));
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