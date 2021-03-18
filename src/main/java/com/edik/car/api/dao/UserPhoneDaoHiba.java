package com.edik.car.api.dao;

import com.edik.car.api.dao.model.UserPhone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserPhoneDaoHiba extends AbstractDaoHiba<UserPhone> {

    private static volatile UserPhoneDaoHiba userPhoneDaoInstance;

    @Override
    public Class<UserPhone> getEntityType() {
        return UserPhone.class;
    }

    public static UserPhoneDaoHiba getInstance() {
        UserPhoneDaoHiba localInstance = userPhoneDaoInstance;

        if (localInstance == null) {

            synchronized (UserPhoneDaoHiba.class) {
                localInstance = userPhoneDaoInstance;

                if (localInstance == null) {
                    userPhoneDaoInstance = localInstance = new UserPhoneDaoHiba();
                }
            }
        }

        return localInstance;
    }
}
