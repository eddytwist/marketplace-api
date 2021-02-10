package by.edik.car_api.db.exception;

public class DbManagerException extends DbException{
    public DbManagerException() {
        super("Getting connection failed.");
    }
}
