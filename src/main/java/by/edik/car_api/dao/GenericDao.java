package by.edik.car_api.dao;

import java.util.List;

public interface GenericDao<T> {

    T create(T t);

    T getById(Long id);

    List<T> getAll();

    void update(T t);

    void delete(Long id);
}
