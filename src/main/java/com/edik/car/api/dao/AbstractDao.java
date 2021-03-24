package com.edik.car.api.dao;

import java.util.List;

import static com.edik.car.api.dao.db.EntityManagerProvider.getEntityManager;

public abstract class AbstractDao<T> implements GenericDao<T> {

    @Override
    public T save(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public T findById(Long id) {
        return getEntityManager().find(getEntityType(), id);
    }

    @Override
    public List<T> findAll() {
        return getEntityManager()
            .createQuery("from " + getEntityType().getSimpleName(), getEntityType())
            .getResultList();
    }

    @Override
    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    @Override
    public void delete(Long id) {
        T entity = getEntityManager().find(getEntityType(), id);
        getEntityManager().remove(entity);
    }
}
