package by.edik.car_api.dao;

import by.edik.car_api.db.DataSource;
import by.edik.car_api.model.User;
import by.edik.car_api.model.UserInformation;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInformationDaoTest {

    private static final String NAME = "Fedor";

    UserInformationDao userInformationDao = UserInformationDao.getInstance();
    UserDao userDao = UserDao.getInstance();
    UserInformation userInformation;
    User user;

    @BeforeEach
    void setUp() {
        user = userDao.create(new User()
                .setUsername("test")
                .setEmail("test@tut.by")
                .setPassword("pass")
        );
        userInformation = userInformationDao.create(new UserInformation()
                .setUserId(user.getUserId())
                .setName(NAME)
        );
    }

    @AfterEach
    void tearDown() {
        truncateAll();
    }

    @Test
    void create() {
        UserInformation createdUserInformation = userInformationDao.getById(userInformation.getUserId());
        assertTrue(createdUserInformation.getUserId() > 0);
        assertEquals(NAME, createdUserInformation.getName());
        assertEquals(user.getUserId(), createdUserInformation.getUserId());
    }

    @Test
    void getById() {
        UserInformation foundedUserInformation = userInformationDao.getById(userInformation.getUserId());
        assertTrue(foundedUserInformation.getUserId() > 0);
        assertEquals(NAME, foundedUserInformation.getName());
    }

    @Test
    void getAll() {
        User secondUser = userDao.create(user
        .setUsername("testPetr")
        .setEmail("testptr@mail.com")
        );
        userInformationDao.create(userInformation
                .setUserId(secondUser.getUserId())
                .setName("Petr")
        );
        List<UserInformation> foundedUsersInformation = userInformationDao.getAll();
        assertEquals(foundedUsersInformation.size(), 2);
        UserInformation firstUserInformation = foundedUsersInformation.get(0);
        assertTrue(firstUserInformation.getUserId() > 0);
        System.out.println(firstUserInformation.getUserId());
        assertEquals(NAME, firstUserInformation.getName());
        UserInformation secondUserInformation = foundedUsersInformation.get(1);
        System.out.println(secondUserInformation.getUserId());
        System.out.println(secondUser.getUserId());
        assertTrue(secondUserInformation.getUserId() > 0);
        assertEquals(secondUser.getUserId(), secondUserInformation.getUserId());
        assertEquals("Petr", secondUserInformation.getName());
    }

    @Test
    void update() {
        UserInformation foundedUserInformation = userInformationDao.getById(userInformation.getUserId())
                .setName("Petr");
        userInformationDao.update(foundedUserInformation);
        UserInformation updatedUserInformation = userInformationDao.getById(foundedUserInformation.getUserId());
        assertTrue(updatedUserInformation.getUserId() > 0);
        assertEquals(foundedUserInformation.getName(), updatedUserInformation.getName());
        assertEquals(foundedUserInformation.getUserId(), updatedUserInformation.getUserId());
        assertEquals("Petr", updatedUserInformation.getName());
    }

    @Test
    void delete() {
        userInformationDao.delete(userInformation.getUserId());
        assertNull(userInformationDao.getById(userInformation.getUserId()));
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