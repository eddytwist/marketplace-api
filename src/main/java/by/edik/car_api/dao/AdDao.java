package by.edik.car_api.dao;

import by.edik.car_api.model.Ad;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdDao extends AbstractDao<Ad> {

    private static volatile AdDao adDaoInstance;

    @Override
    public Ad create(Ad ad) {
        return null;
    }

    @Override
    public Ad getById(long id) {
        return null;
    }

    @Override
    public List<Ad> getAll() {
        return null;
    }

    @Override
    public void update(Ad ad) {

    }

    @Override
    public void delete(long id) {

    }

    public static AdDao getInstance() {
        AdDao localInstance = adDaoInstance;
        if (localInstance == null) {
            synchronized (AdDao.class) {
                localInstance = adDaoInstance;
                if (localInstance == null) {
                    adDaoInstance = localInstance = new AdDao();
                }
            }
        }
        return localInstance;
    }
}
