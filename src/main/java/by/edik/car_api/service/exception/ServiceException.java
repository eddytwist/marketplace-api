package by.edik.car_api.service.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
