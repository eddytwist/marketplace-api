package by.edik.car_api.dao;

import by.edik.car_api.model.UserPhone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPhoneDao extends AbstractDao<UserPhone> {

    private static volatile UserPhoneDao userPhoneDaoInstance;

    @Override
    public UserPhone create(UserPhone userPhone) {
        return null;
    }

    @Override
    public UserPhone getById(long id) {
        return null;
    }

    @Override
    public List<UserPhone> getAll() {
        return null;
    }

    @Override
    public void update(UserPhone userPhone) {

    }

    @Override
    public void delete(long id) {

    }

    public static UserPhoneDao getInstance() {
        UserPhoneDao localInstance = userPhoneDaoInstance;
        if (localInstance == null) {
            synchronized (UserPhoneDao.class) {
                localInstance = userPhoneDaoInstance;
                if (localInstance == null) {
                    userPhoneDaoInstance = localInstance = new UserPhoneDao();
                }
            }
        }
        return localInstance;
    }
}
