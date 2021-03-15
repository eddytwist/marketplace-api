package by.edik.car_api.dao;

import javax.persistence.Query;

import static by.edik.car_api.dao.db.EntityManagerProvider.getEntityManager;

public abstract class AbstractDaoHiba<T> implements GenericDao<T> {

    protected void persist(T t) {
        getEntityManager().persist(t);
    }

    protected T find(Class<T> entityClass, Long pkey) {
        return getEntityManager().find(entityClass, pkey);
    }

    protected void remove(T t) {
        getEntityManager().remove(t);
    }

    protected void merge(T t) {
        getEntityManager().merge(t);
    }

    protected T getReference(Class<T> entityClass, Long pkey) {
        return getEntityManager().getReference(entityClass, pkey);
    }

    protected void flush() {
        getEntityManager().flush();
    }

    protected void detach(T t) {
        getEntityManager().detach(t);
    }

    protected Query createQuery(String jpql) {
        return getEntityManager().createQuery(jpql);
    }
}
