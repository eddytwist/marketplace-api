package by.edik.car_api.service.exception;

public class ServiceFailedException extends ServiceException {
    public ServiceFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
