package by.edik.car_api.service;

import by.edik.car_api.dao.PictureDao;
import by.edik.car_api.model.Picture;

import java.util.List;

public class PictureService implements Service<Picture> {

    private static volatile PictureService pictureServiceInstance;

    private final PictureDao pictureDao = PictureDao.getInstance();

    @Override
    public Picture create(Picture picture) {
        return pictureDao.create(picture);
    }

    @Override
    public Picture getById(long id) {
        return pictureDao.getById(id);
    }

    @Override
    public List<Picture> getAll() {
        return pictureDao.getAll();
    }

    @Override
    public void update(Picture picture) {
        pictureDao.update(picture);
    }

    @Override
    public void delete(long id) {
        pictureDao.delete(id);
    }

    public static PictureService getInstance() {
        PictureService localInstance = pictureServiceInstance;
        if (localInstance == null) {
            synchronized (PictureService.class) {
                localInstance = pictureServiceInstance;
                if (localInstance == null) {
                    pictureServiceInstance = localInstance = new PictureService();
                }
            }
        }
        return localInstance;
    }
}
