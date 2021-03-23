package com.edik.car.api.service.exception;

public class ServiceEntityNotFoundException extends ServiceException {
    public ServiceEntityNotFoundException(String type, Long id) {
        super(String.format("Can not found object with type: %s and id: %s", type, id));
    }
}
