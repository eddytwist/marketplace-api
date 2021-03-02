package by.edik.car_api.dao.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class DataSource {

    private static final HikariConfig CONFIG = new HikariConfig("/db/datasource.properties");
    private static final HikariDataSource DATA_SOURCE;

    static {
        DATA_SOURCE = new HikariDataSource(CONFIG);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}
