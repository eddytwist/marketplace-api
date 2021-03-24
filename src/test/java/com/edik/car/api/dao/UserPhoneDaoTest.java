package com.edik.car.api.dao;

import com.edik.car.api.dao.db.DataSource;
import com.edik.car.api.dao.model.User;
import com.edik.car.api.dao.model.UserPhone;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserPhoneDaoTest {

    private static final String PHONE_NUMBER = "+375291234567";

    UserPhoneDao userPhoneDao = UserPhoneDao.getInstance();
    UserDao userDao = UserDao.getInstance();
    UserPhone userPhone;
    User user;

    @BeforeEach
    void setUp() {
        user = userDao.save(new User()
            .setUsername("test")
            .setEmail("test@tut.by")
            .setPassword("pass")
        );
        userPhone = userPhoneDao.save(new UserPhone()
//            .setUserId(user.getUserId())
            .setPhoneNumber(PHONE_NUMBER)
        );
    }

    @AfterEach
    void tearDown() {
        truncateAll();
    }

    @Test
    void create() {
        UserPhone createdUserPhone = userPhoneDao.findById(userPhone.getPhoneNumberId());
//        assertTrue(createdUserPhone.getUserId() > 0);
        assertEquals(PHONE_NUMBER, createdUserPhone.getPhoneNumber());
//        assertEquals(user.getUserId(), createdUserPhone.getUserId());
    }

    @Test
    void getById() {
        UserPhone foundedUserPhone = userPhoneDao.findById(userPhone.getPhoneNumberId());
//        assertTrue(foundedUserPhone.getUserId() > 0);
        assertEquals(PHONE_NUMBER, foundedUserPhone.getPhoneNumber());
    }

    @Test
    void getAll() {
        userPhoneDao.save(userPhone
//            .setUserId(user.getUserId())
                .setPhoneNumber("+375297777777")
        );
        List<UserPhone> foundedUserPhones = userPhoneDao.findAll();
        assertEquals(foundedUserPhones.size(), 2);

        UserPhone firstUserPhone = foundedUserPhones.get(0);
//        assertTrue(firstUserPhone.getUserId() > 0);
//        assertEquals(user.getUserId(), firstUserPhone.getUserId());
        assertEquals(PHONE_NUMBER, firstUserPhone.getPhoneNumber());

        UserPhone secondUserPhone = foundedUserPhones.get(1);
//        assertTrue(secondUserPhone.getUserId() > 0);
//        assertEquals(user.getUserId(), secondUserPhone.getUserId());
        assertEquals("+375297777777", secondUserPhone.getPhoneNumber());
    }

    @Test
    void update() {
        UserPhone foundedUserPhone = userPhoneDao.findById(userPhone.getPhoneNumberId())
            .setPhoneNumber("+375297777777");
        userPhoneDao.update(foundedUserPhone);
        UserPhone updatedUserPhone = userPhoneDao.findById(foundedUserPhone.getPhoneNumberId());
//        assertTrue(updatedUserPhone.getUserId() > 0);
//        assertEquals(foundedUserPhone.getUserId(), updatedUserPhone.getUserId());
        assertEquals("+375297777777", updatedUserPhone.getPhoneNumber());
    }

    @Test
    void delete() {
//        userPhoneDao.delete(userPhone.getUserId());
//        assertNull(userPhoneDao.findById(userPhone.getUserId()));
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