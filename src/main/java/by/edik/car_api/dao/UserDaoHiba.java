package by.edik.car_api.dao;

import by.edik.car_api.dao.db.ConnectionManagerHiba;
import by.edik.car_api.dao.model.User;

import javax.persistence.EntityManager;

public class UserDaoHiba {

    public User create(User user) {
        EntityManager em = ConnectionManagerHiba.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return em.find(User.class, user.getUserId());
    }
}
