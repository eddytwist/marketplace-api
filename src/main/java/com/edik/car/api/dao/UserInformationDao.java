package com.edik.car.api.dao;

import com.edik.car.api.dao.model.UserInformation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserInformationDao extends AbstractDao<UserInformation> {

    private static volatile UserInformationDao userInformationDaoInstance;

    @Override
    public Class<UserInformation> getEntityType() {
        return UserInformation.class;
    }

    public static UserInformationDao getInstance() {
        UserInformationDao localInstance = userInformationDaoInstance;

        if (localInstance == null) {

            synchronized (UserInformationDao.class) {
                localInstance = userInformationDaoInstance;

                if (localInstance == null) {
                    userInformationDaoInstance = localInstance = new UserInformationDao();
                }
            }
        }

        return localInstance;
    }
}
