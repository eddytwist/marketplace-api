package by.edik.car_api.service;

import by.edik.car_api.db.DataSource;
import by.edik.car_api.model.User;
import by.edik.car_api.model.UserInformation;
import by.edik.car_api.service.impl.UserInformationServiceImpl;
import by.edik.car_api.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInformationServiceImplTest {

    private static final String NAME = "Fedor";

    UserInformationServiceImpl userInformationServiceImpl = UserInformationServiceImpl.getInstance();
    UserServiceImpl userService = UserServiceImpl.getInstance();
    UserInformation userInformation;
    User user;

    @BeforeEach
    void setUp() {
        user = userService.create(new User()
                .setUsername("test")
                .setEmail("test@tut.by")
                .setPassword("pass")
        );
        userInformation = userInformationServiceImpl.create(new UserInformation()
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
        UserInformation createdUserInformation = userInformationServiceImpl.getById(userInformation.getUserId());
        assertTrue(createdUserInformation.getUserId() > 0);
        assertEquals(NAME, createdUserInformation.getName());
        assertEquals(user.getUserId(), createdUserInformation.getUserId());
    }

    @Test
    void getById() {
        UserInformation foundedUserInformation = userInformationServiceImpl.getById(userInformation.getUserId());
        assertTrue(foundedUserInformation.getUserId() > 0);
        assertEquals(NAME, foundedUserInformation.getName());
    }

    @Test
    void getAll() {
        User secondUser = userService.create(user
                .setUsername("testPetr")
                .setEmail("testptr@mail.com")
        );
        userInformationServiceImpl.create(userInformation
                .setUserId(secondUser.getUserId())
                .setName("Petr")
        );
        List<UserInformation> foundedUsersInformation = userInformationServiceImpl.getAll();
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
        UserInformation foundedUserInformation = userInformationServiceImpl.getById(userInformation.getUserId())
                .setName("Petr");
        userInformationServiceImpl.update(foundedUserInformation);
        UserInformation updatedUserInformation = userInformationServiceImpl.getById(foundedUserInformation.getUserId());
        assertTrue(updatedUserInformation.getUserId() > 0);
        assertEquals(foundedUserInformation.getName(), updatedUserInformation.getName());
        assertEquals(foundedUserInformation.getUserId(), updatedUserInformation.getUserId());
        assertEquals("Petr", updatedUserInformation.getName());
    }

    @Test
    void delete() {
        userInformationServiceImpl.delete(userInformation.getUserId());
        assertNull(userInformationServiceImpl.getById(userInformation.getUserId()));
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