package by.edik.car_api.dao;

import by.edik.car_api.db.DataSource;
import by.edik.car_api.model.Ad;
import by.edik.car_api.model.Condition;
import by.edik.car_api.model.Picture;
import by.edik.car_api.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PictureDaoTest {

    private static final String REFERENCE = "reference";

    PictureDao pictureDao = PictureDao.getInstance();
    AdDao adDao = AdDao.getInstance();
    UserDao userDao = UserDao.getInstance();
    Picture picture;
    Ad ad;
    User user;

    @BeforeEach
    void setUp() {
        user = userDao.create(new User()
                .setUsername("test")
                .setEmail("test@tut.by")
                .setPassword("pass")
        );
        ad = adDao.create(new Ad()
                .setUserId(user.getUserId())
                .setYear(2020)
                .setBrand("JIGULI")
                .setModel("2107")
                .setEngineVolume(0)
                .setCondition(Condition.NEW)
                .setMileage(0L)
                .setEnginePower(0)
                .setCreationTime(LocalDateTime.of(2021, 2, 8, 12, 20))
                .setEditingTime(LocalDateTime.of(2021, 2, 16, 12, 20))
        );
        picture = pictureDao.create(new Picture()
                .setAdId(ad.getAdId())
                .setReference(REFERENCE));
    }

    @AfterEach
    void tearDown() {
        truncateAll();
    }


    @Test
    void create() {
        Picture createdPicture = pictureDao.create(picture);
        assertTrue(createdPicture.getPictureId() > 0);
        assertTrue(createdPicture.getAdId() > 0);
        assertEquals(REFERENCE, createdPicture.getReference());
    }

    @Test
    void getById() {
        Picture foundedPicture = pictureDao.getById(picture.getPictureId());
        assertTrue(foundedPicture.getPictureId() > 0);
        assertTrue(foundedPicture.getAdId() > 0);
        assertEquals(REFERENCE, foundedPicture.getReference());
    }

    @Test
    void getAll() {
        pictureDao.create(picture
                .setReference("new_reference")
        );
        List<Picture> foundedPictures = pictureDao.getAll();
        assertEquals(foundedPictures.size(), 2);

        Picture firstPicture = foundedPictures.get(0);
        assertTrue(firstPicture.getAdId() > 0);
        assertTrue(firstPicture.getPictureId() > 0);
        assertEquals(REFERENCE, firstPicture.getReference());

        Picture secondPicture = foundedPictures.get(1);
        assertTrue(secondPicture.getPictureId() > 0);
        assertTrue(secondPicture.getAdId() > 0);
        assertEquals("new_reference", secondPicture.getReference());
    }

    @Test
    void update() {
        Picture foundedPicture = pictureDao.getById(picture.getPictureId())
                .setReference("NEW_REFERENCE");
        pictureDao.update(foundedPicture);
        Picture updatedPicture = pictureDao.getById(foundedPicture.getPictureId());
        assertTrue(updatedPicture.getPictureId() > 0);
        assertTrue(updatedPicture.getAdId() > 0);
        assertEquals(foundedPicture.getPictureId(), updatedPicture.getPictureId());
        assertEquals(foundedPicture.getAdId(), updatedPicture.getAdId());
        assertEquals("NEW_REFERENCE", updatedPicture.getReference());
    }

    @Test
    void delete() {
        pictureDao.delete(picture.getPictureId());
        assertNull(pictureDao.getById(picture.getPictureId()));
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