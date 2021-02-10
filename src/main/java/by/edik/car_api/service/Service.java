package by.edik.car_api.service;

import java.util.List;

public interface Service<T> {
    T create(T t);

    T getById(long id);

    List<T> getAll();

    void update(T t);

    void delete(long id);
}
