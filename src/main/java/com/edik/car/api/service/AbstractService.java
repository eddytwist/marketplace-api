package com.edik.car.api.service;

import com.edik.car.api.dao.db.EntityManagerProvider;

public abstract class AbstractService {

    protected static void begin() {
        EntityManagerProvider.getEntityManager().getTransaction().begin();
    }

    protected static void commit() {
        EntityManagerProvider.getEntityManager().getTransaction().commit();
        EntityManagerProvider.clear();
    }

    protected static void rollback() {
        EntityManagerProvider.getEntityManager().getTransaction().rollback();
        EntityManagerProvider.clear();
    }
}
