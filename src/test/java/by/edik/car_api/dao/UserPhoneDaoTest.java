package by.edik.car_api.dao;

import by.edik.car_api.db.DataSource;
import by.edik.car_api.model.User;
import by.edik.car_api.model.UserPhone;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserPhoneDaoTest {

    private static final String PHONE_NUMBER = "+375291234567";

    UserPhoneDao userPhoneDao = UserPhoneDao.getInstance();
    UserDao userDao = UserDao.getInstance();
    UserPhone userPhone;
    User user;

    @BeforeEach
    void setUp() {
        user = userDao.create(new User()
                .setUsername("test")
                .setEmail("test@tut.by")
                .setPassword("pass")
        );
        userPhone = userPhoneDao.create(new UserPhone()
                .setUserId(user.getUserId())
                .setPhoneNumber(PHONE_NUMBER)
        );
    }

    @AfterEach
    void tearDown() {
        truncateAll();
    }

    @Test
    void create() {
        UserPhone createdUserPhone = userPhoneDao.getById(userPhone.getPhoneNumberId());
        assertTrue(createdUserPhone.getUserId() > 0);
        assertEquals(PHONE_NUMBER, createdUserPhone.getPhoneNumber());
        assertEquals(user.getUserId(), createdUserPhone.getUserId());
    }

    @Test
    void getById() {
        UserPhone foundedUserPhone = userPhoneDao.getById(userPhone.getPhoneNumberId());
        assertTrue(foundedUserPhone.getUserId() > 0);
        assertEquals(PHONE_NUMBER, foundedUserPhone.getPhoneNumber());
    }

    @Test
    void getAll() {
        userPhoneDao.create(userPhone
                .setUserId(user.getUserId())
                .setPhoneNumber("+375297777777")
        );
        List<UserPhone> foundedUserPhones = userPhoneDao.getAll();
        assertEquals(foundedUserPhones.size(), 2);

        UserPhone firstUserPhone = foundedUserPhones.get(0);
        assertTrue(firstUserPhone.getUserId() > 0);
        assertEquals(user.getUserId(), firstUserPhone.getUserId());
        assertEquals(PHONE_NUMBER, firstUserPhone.getPhoneNumber());

        UserPhone secondUserPhone = foundedUserPhones.get(1);
        assertTrue(secondUserPhone.getUserId() > 0);
        assertEquals(user.getUserId(), secondUserPhone.getUserId());
        assertEquals("+375297777777", secondUserPhone.getPhoneNumber());
    }

    @Test
    void update() {
        UserPhone foundedUserPhone = userPhoneDao.getById(userPhone.getPhoneNumberId())
                .setPhoneNumber("+375297777777");
        userPhoneDao.update(foundedUserPhone);
        UserPhone updatedUserPhone = userPhoneDao.getById(foundedUserPhone.getPhoneNumberId());
        assertTrue(updatedUserPhone.getUserId() > 0);
        assertEquals(foundedUserPhone.getUserId(), updatedUserPhone.getUserId());
        assertEquals("+375297777777", updatedUserPhone.getPhoneNumber());
    }

    @Test
    void delete() {
        userPhoneDao.delete(userPhone.getUserId());
        assertNull(userPhoneDao.getById(userPhone.getUserId()));
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