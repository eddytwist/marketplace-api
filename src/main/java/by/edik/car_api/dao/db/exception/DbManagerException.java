package by.edik.car_api.dao.db.exception;

public class DbManagerException extends DbException {
    public DbManagerException() {
        super("Getting connection failed.");
    }
}
