package by.edik.car_api.service;

import by.edik.car_api.dao.AdDao;
import by.edik.car_api.model.Ad;

import java.util.List;

public class AdService implements Service<Ad> {

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
}
