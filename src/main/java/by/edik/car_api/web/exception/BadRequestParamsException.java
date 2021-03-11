package by.edik.car_api.web.exception;

public class BadRequestParamsException extends BadRequestException {

    public BadRequestParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestParamsException(String message) {
        super(message);
    }
}
