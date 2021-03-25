package com.edik.car.api.dao;

import com.edik.car.api.dao.db.EntityManagerProvider;
import com.edik.car.api.dao.model.User;
import org.springframework.stereotype.Repository;

@Repository(value = "userDao")
public class UserDao extends AbstractDao<User> {

    @Override
    public Class<User> getEntityType() {
        return User.class;
    }

    public User findByIdWithUserInfoAndPhones(Long id) {
        return EntityManagerProvider.getEntityManager()
            .createQuery("select user" +
                " from User user" +
                " left join  user.userInformation" +
                " left join fetch user.userPhones" +
                " where user.userId = :id", User.class)
            .setParameter("id", id)
            .getSingleResult();
    }
}
