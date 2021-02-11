package by.edik.car_api.dao;

import by.edik.car_api.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao extends AbstractDao<User> {

    private static volatile UserDao userDaoInstance;

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(long id) {

    }

    public static UserDao getInstance() {
        UserDao localInstance = userDaoInstance;
        if (localInstance == null) {
            synchronized (UserDao.class) {
                localInstance = userDaoInstance;
                if (localInstance == null) {
                    userDaoInstance = localInstance = new UserDao();
                }
            }
        }
        return localInstance;
    }
}
