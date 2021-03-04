package by.edik.car_api.web.exception;

public class ServerFailedException extends RuntimeException {

    public ServerFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
