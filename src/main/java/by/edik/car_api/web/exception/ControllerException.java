package by.edik.car_api.web.exception;

public class ControllerException extends RuntimeException {
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(String message) {
        super(message);
    }
}
