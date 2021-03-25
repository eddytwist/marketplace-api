package com.edik.car.api.dao;

import com.edik.car.api.dao.model.UserInformation;
import org.springframework.stereotype.Repository;

@Repository(value = "userInformationDao")
public final class UserInformationDao extends AbstractDao<UserInformation> {

    @Override
    public Class<UserInformation> getEntityType() {
        return UserInformation.class;
    }
}
