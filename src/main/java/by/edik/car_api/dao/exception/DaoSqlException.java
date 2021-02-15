package by.edik.car_api.dao.exception;

public class DaoSqlException extends DaoException{
    public DaoSqlException(Exception e) {
        super("SQL Exception: " + e.getMessage());
    }
}
