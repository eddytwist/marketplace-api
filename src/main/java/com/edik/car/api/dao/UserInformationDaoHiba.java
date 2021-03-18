package com.edik.car.api.dao;

import com.edik.car.api.dao.model.UserInformation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserInformationDaoHiba extends AbstractDaoHiba<UserInformation> {

    private static volatile UserInformationDaoHiba userInformationDaoInstance;

    @Override
    public Class<UserInformation> getEntityType() {
        return UserInformation.class;
    }

    public static UserInformationDaoHiba getInstance() {
        UserInformationDaoHiba localInstance = userInformationDaoInstance;

        if (localInstance == null) {

            synchronized (UserInformationDaoHiba.class) {
                localInstance = userInformationDaoInstance;

                if (localInstance == null) {
                    userInformationDaoInstance = localInstance = new UserInformationDaoHiba();
                }
            }
        }

        return localInstance;
    }
}
