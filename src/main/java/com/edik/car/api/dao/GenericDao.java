package com.edik.car.api.dao;

import java.util.List;

public interface GenericDao<T> {

    Class<T> getEntityType();

    T save(T t);

    T findById(Long id);

    List<T> findAll();

    T update(T t);

    void delete(Long id);
}
