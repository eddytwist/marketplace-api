package com.edik.car.api.dao.db;

import com.edik.car.api.dao.db.exception.DbManagerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionManager {

    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    public static Connection getConnection() {
        try {
            if (THREAD_LOCAL.get() == null) {
                THREAD_LOCAL.set(DataSource.getConnection());
            }
            return THREAD_LOCAL.get();
        } catch (Exception e) {
            throw new DbManagerException("Getting connection failed.", e);
        }
    }
}
