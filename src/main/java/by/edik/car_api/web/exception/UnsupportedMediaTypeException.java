package by.edik.car_api.web.exception;

public class UnsupportedMediaTypeException extends BadRequestException {

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }
}
