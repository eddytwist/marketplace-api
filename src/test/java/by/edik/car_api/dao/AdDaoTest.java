package by.edik.car_api.dao;

import by.edik.car_api.model.Ad;
import by.edik.car_api.model.Condition;
import by.edik.car_api.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdDaoTest {

    AdDao adDao = AdDao.getInstance();
    UserDao userDao = UserDao.getInstance();
    User user;
    Ad ad;




    @BeforeEach
    void setUp() {
        user = userDao.create(new User()
                .setUsername("test")
                .setEmail("test@tut.by")
                .setPassword("pass"));
        ad = new Ad()
                .setUserId(user.getUserId())
                .setYear(1999)
                .setBrand("brand")
                .setModel("model")
                .setEngineVolume(1500)
                .setCondition(Condition.USED)
                .setMileage(200000)
                .setEnginePower(99)
                .setCreationTime(LocalDateTime.now().minusMinutes(10))
                .setEditingTime(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() {
        adDao.delete(ad.getAdId());
        userDao.delete(user.getUserId());
    }

    @Test
    void create() {
        Ad createdAd = adDao.create(ad);

        assertTrue(ad.getUserId() > 0);
    }

    @Test
    void getById() {

        Ad foundedAd = adDao.getById(ad.getAdId());

        //then
        assertTrue(user.getUserId() > 0);
        assertEquals("brand", foundedAd.getBrand());
    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}