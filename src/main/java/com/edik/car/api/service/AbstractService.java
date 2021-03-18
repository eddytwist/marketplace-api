package com.edik.car.api.service;

import com.edik.car.api.dao.db.ConnectionManager;
import com.edik.car.api.dao.db.exception.DbManagerException;

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
