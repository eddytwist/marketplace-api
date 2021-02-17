package by.edik.car_api.service;

import by.edik.car_api.dao.UserPhoneDao;
import by.edik.car_api.model.UserPhone;

import java.util.List;

public class UserPhoneService implements Service<UserPhone> {

    private static volatile UserPhoneService userPhoneServiceInstance;

    private final UserPhoneDao userPhoneDao = UserPhoneDao.getInstance();

    @Override
    public UserPhone create(UserPhone userPhone) {
        return userPhoneDao.create(userPhone);
    }

    @Override
    public UserPhone getById(long id) {
        return userPhoneDao.getById(id);
    }

    @Override
    public List<UserPhone> getAll() {
        return userPhoneDao.getAll();
    }

    @Override
    public void update(UserPhone userPhone) {
        userPhoneDao.update(userPhone);
    }

    @Override
    public void delete(long id) {
        userPhoneDao.delete(id);
    }

    public static UserPhoneService getInstance() {
        UserPhoneService localInstance = userPhoneServiceInstance;
        if (localInstance == null) {
            synchronized (UserPhoneService.class) {
                localInstance = userPhoneServiceInstance;
                if (localInstance == null) {
                    userPhoneServiceInstance = localInstance = new UserPhoneService();
                }
            }
        }
        return localInstance;
    }
}
