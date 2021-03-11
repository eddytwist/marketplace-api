package by.edik.car_api.dao.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataSource {

    private static final HikariConfig CONFIG = new HikariConfig("/db/datasource.properties");
    private static final HikariDataSource DATA_SOURCE;

    static {
        DATA_SOURCE = new HikariDataSource(CONFIG);
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}
