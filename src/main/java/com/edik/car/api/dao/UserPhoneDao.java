package com.edik.car.api.dao;

import com.edik.car.api.dao.model.UserPhone;
import org.springframework.stereotype.Repository;

@Repository(value = "userPhoneDao")
public final class UserPhoneDao extends AbstractDao<UserPhone> {

    @Override
    public Class<UserPhone> getEntityType() {
        return UserPhone.class;
    }
}
