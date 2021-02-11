package by.edik.car_api.dao;

import by.edik.car_api.model.Picture;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PictureDao extends AbstractDao<Picture> {

    private static volatile PictureDao pictureDaoInstance;

    @Override
    public Picture create(Picture picture) {
        return null;
    }

    @Override
    public Picture getById(long id) {
        return null;
    }

    @Override
    public List<Picture> getAll() {
        return null;
    }

    @Override
    public void update(Picture picture) {

    }

    @Override
    public void delete(long id) {

    }

    public static PictureDao getInstance() {
        PictureDao localInstance = pictureDaoInstance;
        if (localInstance == null) {
            synchronized (PictureDao.class) {
                localInstance = pictureDaoInstance;
                if (localInstance == null) {
                    pictureDaoInstance = localInstance = new PictureDao();
                }
            }
        }
        return localInstance;
    }
}
