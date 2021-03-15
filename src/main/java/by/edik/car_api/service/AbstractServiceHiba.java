package by.edik.car_api.service;

import static by.edik.car_api.dao.db.EntityManagerProvider.clear;
import static by.edik.car_api.dao.db.EntityManagerProvider.getEntityManager;

public abstract class AbstractServiceHiba {

    protected void begin() {
        getEntityManager().getTransaction().begin();
    }

    protected void commit() {
        getEntityManager().getTransaction().commit();
        clear();
    }

    protected void rollback() {
        getEntityManager().getTransaction().rollback();
        clear();
    }
}
