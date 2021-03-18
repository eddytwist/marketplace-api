package com.edik.car.api.dao.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataSource {

    private static final HikariConfig CONFIG = new HikariConfig("/WEB-INF/hibernate.properties");
    private static final HikariDataSource DATA_SOURCE;

    static {
        try {
            DATA_SOURCE = new HikariDataSource(CONFIG);
        } catch (Exception e) {
            log.error("Can not init datasource", e);
            throw e;
        }
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}
