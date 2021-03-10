package by.edik.car_api.service;

import by.edik.car_api.dao.db.ConnectionManager;
import by.edik.car_api.dao.db.exception.DbManagerException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractService {
    protected static void startTransaction() throws SQLException {
        ConnectionManager.getConnection().setAutoCommit(false);
    }

    protected static void commit() throws SQLException {
        ConnectionManager.getConnection().commit();
    }

    protected static Connection getConnection() {
        return ConnectionManager.getConnection();
    }

    protected static void rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            throw new DbManagerException("Rollback failed.", e);
        }
    }
}
