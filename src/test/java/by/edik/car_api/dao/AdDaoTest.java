package by.edik.car_api.dao;

import by.edik.car_api.model.Ad;
import by.edik.car_api.model.Condition;
import by.edik.car_api.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdDaoTest {

    AdDao adDao = AdDao.getInstance();
    UserDao userDao = UserDao.getInstance();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        //given
        User user = userDao.create(
                new User()
                        .setUsername("test")
                        .setEmail("test@tut.by")
                        .setPassword("pass")
        );

        Ad ad = new Ad()
                .setUserId(user.getUserId())
                .setYear((short) 1999)
                .setBrand("brand")
                .setModel("model")
                .setEngineVolume(1500)
                .setCondition(Condition.USED)
                .setMileage(200000)
                .setEnginePower(99)
                .setCreationTime(new Date(2021,2,8))
                .setEditingTime(new Date(2021,2,10));
//        Ad add = new Ad(,
//                user.getUserId(),
//                2000,
//                "ww",
//                "ff",
//                1000,
//                Condition.USED,
//                150000,
//                120,
//                new Date(2021,2,8),
//                new Date(2021,2,8));

        //when
        Ad createdAd = adDao.create(ad);
        Ad foundedAd = adDao.getById(ad.getAdId());

        //then
        assertTrue(user.getUserId() > 0);
        assertEquals("brand", foundedAd.getBrand());
    }

    @Test
    void getById() {
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