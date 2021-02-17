package by.edik.car_api.service;

import by.edik.car_api.dao.AdDao;
import by.edik.car_api.model.Ad;

import java.util.List;

public class AdService implements Service<Ad> {

    private static volatile AdService adServiceInstance;

    private final AdDao adDao = AdDao.getInstance();

    @Override
    public Ad create(Ad ad) {
        return adDao.create(ad);
    }

    @Override
    public Ad getById(long id) {
        return adDao.getById(id);
    }

    @Override
    public List<Ad> getAll() {
        return adDao.getAll();
    }

    @Override
    public void update(Ad ad) {
        adDao.update(ad);
    }

    @Override
    public void delete(long id) {
        adDao.delete(id);
    }

    public void updateAllowedFields (Ad ad) {
        adDao.updateAllowedFields(ad);
    }

    public static AdService getInstance() {
        AdService localInstance = adServiceInstance;
        if (localInstance == null) {
            synchronized (AdService.class) {
                localInstance = adServiceInstance;
                if (localInstance == null) {
                    adServiceInstance = localInstance = new AdService();
                }
            }
        }
        return localInstance;
    }
}
