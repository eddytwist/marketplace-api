package by.edik.car_api.dao;

import by.edik.car_api.db.DataSource;
import by.edik.car_api.model.Picture;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

class PictureDaoTest {

    private static final String REFERENCE = "REFERENCE";

    PictureDao pictureDao = PictureDao.getInstance();
    Picture picture;

    @BeforeEach
    void setUp() {
        picture = pictureDao.create(new Picture()
                .setReference(REFERENCE)
        );
    }

    @AfterEach
    void tearDown() {
        truncateAll();
    }


    @Test
    void create() {
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