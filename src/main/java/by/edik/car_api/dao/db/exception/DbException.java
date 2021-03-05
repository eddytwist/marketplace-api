package by.edik.car_api.dao.db.exception;

public class DbException extends RuntimeException {
    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
}
