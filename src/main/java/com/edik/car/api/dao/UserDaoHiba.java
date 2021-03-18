package com.edik.car.api.dao;

import com.edik.car.api.dao.model.User;

public class UserDaoHiba extends AbstractDaoHiba<User> {

    private static volatile UserDaoHiba userDaoHibaInstance;

    @Override
    public Class<User> getEntityType() {
        return User.class;
    }

    public static UserDaoHiba getInstance() {
        UserDaoHiba localInstance = userDaoHibaInstance;

        if (localInstance == null) {

            synchronized (UserDaoHiba.class) {
                localInstance = userDaoHibaInstance;

                if (localInstance == null) {
                    userDaoHibaInstance = localInstance = new UserDaoHiba();
                }
            }
        }

        return localInstance;
    }
}
