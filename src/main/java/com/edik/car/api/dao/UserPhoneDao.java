package com.edik.car.api.dao;

import com.edik.car.api.dao.model.UserPhone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserPhoneDao extends AbstractDao<UserPhone> {

    private static volatile UserPhoneDao userPhoneDaoInstance;

    @Override
    public Class<UserPhone> getEntityType() {
        return UserPhone.class;
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
