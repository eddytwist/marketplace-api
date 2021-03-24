package com.edik.car.api.dao.exception;

public class ObjectNotFoundException extends DaoException {
    public ObjectNotFoundException(String type, Long id) {
        super(String.format("Can not found object with type: %s and id: %s", type, id));
    }
}
