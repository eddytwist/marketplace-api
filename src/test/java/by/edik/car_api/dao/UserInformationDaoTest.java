package by.edik.car_api.dao;

import by.edik.car_api.dao.db.DataSource;
import by.edik.car_api.dao.model.User;
import by.edik.car_api.dao.model.UserInformation;
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

class UserInformationDaoTest {

    private static final String NAME = "Fedor";

    UserInformationDao userInformationDao = UserInformationDao.getInstance();
    UserDao userDao = UserDao.getInstance();
    UserInformation userInformation;
    User user;

    @BeforeEach
    void setUp() {
        user = userDao.save(new User()
            .setUsername("test")
            .setEmail("test@tut.by")
            .setPassword("pass")
        );
        userInformation = userInformationDao.save(new UserInformation()
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
        UserInformation createdUserInformation = userInformationDao.findById(userInformation.getUserId());
        assertTrue(createdUserInformation.getUserId() > 0);
        assertEquals(NAME, createdUserInformation.getName());
        assertEquals(user.getUserId(), createdUserInformation.getUserId());
    }

    @Test
    void getById() {
        UserInformation foundedUserInformation = userInformationDao.findById(userInformation.getUserId());
        assertTrue(foundedUserInformation.getUserId() > 0);
        assertEquals(NAME, foundedUserInformation.getName());
    }

    @Test
    void getAll() {
        User secondUser = userDao.save(user
            .setUsername("testPetr")
            .setEmail("testptr@mail.com")
        );
        userInformationDao.save(userInformation
            .setUserId(secondUser.getUserId())
            .setName("Petr")
        );
        List<UserInformation> foundedUsersInformation = userInformationDao.findAll();
        assertEquals(foundedUsersInformation.size(), 2);

        UserInformation firstUserInformation = foundedUsersInformation.get(0);
        assertTrue(firstUserInformation.getUserId() > 0);
        assertEquals(NAME, firstUserInformation.getName());

        UserInformation secondUserInformation = foundedUsersInformation.get(1);
        assertTrue(secondUserInformation.getUserId() > 0);
        assertEquals(secondUser.getUserId(), secondUserInformation.getUserId());
        assertEquals("Petr", secondUserInformation.getName());
    }

    @Test
    void update() {
        UserInformation foundedUserInformation = userInformationDao.findById(userInformation.getUserId())
            .setName("Petr");
        userInformationDao.update(foundedUserInformation);
        UserInformation updatedUserInformation = userInformationDao.findById(foundedUserInformation.getUserId());
        assertTrue(updatedUserInformation.getUserId() > 0);
        assertEquals(foundedUserInformation.getName(), updatedUserInformation.getName());
        assertEquals(foundedUserInformation.getUserId(), updatedUserInformation.getUserId());
        assertEquals("Petr", updatedUserInformation.getName());
    }

    @Test
    void delete() {
        userInformationDao.delete(userInformation.getUserId());
        assertNull(userInformationDao.findById(userInformation.getUserId()));
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