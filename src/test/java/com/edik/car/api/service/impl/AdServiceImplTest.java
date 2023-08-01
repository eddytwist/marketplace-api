package com.edik.car.api.service.impl;

import com.edik.car.api.dao.AdDao;
import com.edik.car.api.dao.db.DataSource;
import com.edik.car.api.dao.model.Condition;
import com.edik.car.api.service.AdService;
import com.edik.car.api.service.UserService;
import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.CreateUserRequest;
import com.edik.car.api.web.dto.response.AdResponse;
import com.edik.car.api.web.dto.response.UserResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdServiceImplTest {
    private static final Integer YEAR = 1999;
    private static final String BRAND = "Lada";
    private static final String MODEL = "Kalina";
    private static final Integer ENGINE_VOLUME = 1600;
    private static final Long MILEAGE = 350555L;
    private static final Integer ENGINE_POWER = 80;
    private static final LocalDateTime CREATION_TIME = LocalDateTime.of(2021, 2, 8, 12, 20);
    private static final LocalDateTime EDITING_TIME = LocalDateTime.of(2021, 2, 15, 16, 20);

    AdService adService = AdServiceImpl.getInstance();
    AdDao adDao = AdDao.getInstance();
    UserService userService = UserServiceImpl.getInstance();
    UserResponse user;
    AdResponse ad;

    @BeforeEach
    void setUp() {
        user = userService.create(new CreateUserRequest()
            .setUsername("test")
            .setEmail("test@tut.by")
            .setPassword("pass")
            .setUserPhones(new ArrayList<>()));

        ad = adService.create(new CreateAdRequest()
            .setUserId(user.getUserId())
            .setYear(YEAR)
            .setBrand(BRAND)
            .setModel(MODEL)
            .setEngineVolume(ENGINE_VOLUME)
            .setCondition(Condition.USED)
            .setMileage(MILEAGE)
            .setEnginePower(ENGINE_POWER)
            .setPictureReferences(List.of("dick", "dick")));
    }

    @Test
    void getById() {
        AdResponse byId = adService.getById(ad.getAdId());

        assertNotNull(byId);
    }

    @Test
    void getAll() {
    }

//    @AfterEach
//    void tearDown() {
//        truncateAll();
//    }
//
//    @SneakyThrows
//    private void truncateAll() {
//        Connection connection = DataSource.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(
//            "truncate pictures, ads, user_information, user_phones, users;"
//        );
//        preparedStatement.execute();
//        preparedStatement.close();
//        connection.close();
//    }
}