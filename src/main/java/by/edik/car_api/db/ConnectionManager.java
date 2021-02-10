package by.edik.car_api.db;

import by.edik.car_api.db.exception.DbManagerException;

import java.sql.Connection;

public class ConnectionManager {

    private static final ThreadLocal<Connection> tl = new ThreadLocal<>();

    public static Connection getConnection() throws DbManagerException {
        try {
            if (tl.get() == null) {
                tl.set(DataSource.getConnection());
            }
            return tl.get();
        } catch (Exception e) {
            throw new DbManagerException();
        }
    }

}
