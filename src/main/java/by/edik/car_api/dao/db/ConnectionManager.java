package by.edik.car_api.dao.db;

import by.edik.car_api.dao.db.exception.DbManagerException;

import java.sql.Connection;

public final class ConnectionManager {

    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    private ConnectionManager() {
    }

    public static Connection getConnection() {
        try {
            if (THREAD_LOCAL.get() == null) {
                THREAD_LOCAL.set(DataSource.getConnection());
            }
            return THREAD_LOCAL.get();
        } catch (Exception e) {
            throw new DbManagerException();
        }
    }
}
