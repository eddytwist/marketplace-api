package com.edik.car.api.dao;

import com.edik.car.api.dao.db.EntityManagerProvider;
import com.edik.car.api.dao.model.User;

public class UserDao extends AbstractDao<User> {

    private static volatile UserDao userDaoHibaInstance;

    @Override
    public Class<User> getEntityType() {
        return User.class;
    }

    public User getUserToResponse(Long id) {
        return (User) EntityManagerProvider.getEntityManager()
            .createQuery("select user" +
                " from User user" +
                " left join  user.userInformation" +
                " left join fetch user.userPhones" +
                " where user.userId = :id")
            .setParameter("id", id)
            .getSingleResult();
    }

    public static UserDao getInstance() {
        UserDao localInstance = userDaoHibaInstance;

        if (localInstance == null) {

            synchronized (UserDao.class) {
                localInstance = userDaoHibaInstance;

                if (localInstance == null) {
                    userDaoHibaInstance = localInstance = new UserDao();
                }
            }
        }

        return localInstance;
    }
}
