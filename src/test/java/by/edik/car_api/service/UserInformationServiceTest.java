package by.edik.car_api.service;

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

class UserInformationServiceTest {

    private static final String NAME = "Fedor";

    UserInformationService userInformationService = UserInformationService.getInstance();
    UserService userService = UserService.getInstance();
    UserInformation userInformation;
    User user;

    @BeforeEach
    void setUp() {
        user = userService.create(new User()
                .setUsername("test")
                .setEmail("test@tut.by")
                .setPassword("pass")
        );
        userInformation = userInformationService.create(new UserInformation()
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
        UserInformation createdUserInformation = userInformationService.getById(userInformation.getUserId());
        assertTrue(createdUserInformation.getUserId() > 0);
        assertEquals(NAME, createdUserInformation.getName());
        assertEquals(user.getUserId(), createdUserInformation.getUserId());
    }

    @Test
    void getById() {
        UserInformation foundedUserInformation = userInformationService.getById(userInformation.getUserId());
        assertTrue(foundedUserInformation.getUserId() > 0);
        assertEquals(NAME, foundedUserInformation.getName());
    }

    @Test
    void getAll() {
        User secondUser = userService.create(user
                .setUsername("testPetr")
                .setEmail("testptr@mail.com")
        );
        userInformationService.create(userInformation
                .setUserId(secondUser.getUserId())
                .setName("Petr")
        );
        List<UserInformation> foundedUsersInformation = userInformationService.getAll();
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
        UserInformation foundedUserInformation = userInformationService.getById(userInformation.getUserId())
                .setName("Petr");
        userInformationService.update(foundedUserInformation);
        UserInformation updatedUserInformation = userInformationService.getById(foundedUserInformation.getUserId());
        assertTrue(updatedUserInformation.getUserId() > 0);
        assertEquals(foundedUserInformation.getName(), updatedUserInformation.getName());
        assertEquals(foundedUserInformation.getUserId(), updatedUserInformation.getUserId());
        assertEquals("Petr", updatedUserInformation.getName());
    }

    @Test
    void delete() {
        userInformationService.delete(userInformation.getUserId());
        assertNull(userInformationService.getById(userInformation.getUserId()));
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