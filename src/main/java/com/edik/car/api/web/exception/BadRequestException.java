package com.edik.car.api.web.exception;

public abstract class BadRequestException extends ControllerException {

    protected BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BadRequestException(String message) {
        super(message);
    }
}
