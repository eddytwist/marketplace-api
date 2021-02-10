package by.edik.car_api.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig("datasource.properties" );
    private static HikariDataSource ds;

    static {
        ds = new HikariDataSource( config );
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

/*    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("db");
    private static final String URL = RESOURCE.getString("url");
    private static final String USER = RESOURCE.getString("user");
    private static final String PASSWORD = RESOURCE.getString("password");
    private static final String NAME = RESOURCE.getString("name");
    private static String DRIVER = RESOURCE.getString("driver");

    static {
        ResourceBundle rb = ResourceBundle.getBundle("db");

        if (rb == null) {
            throw new DbManagerException("Бандл для db не был инициализирован.");
        } else {
            URL = rb.getString("url");
            USER = rb.getString("user");
            PASSWORD = rb.getString("password");
            DRIVER = rb.getString("driver");
        }
    }

    private static Connection getInstance() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.;
        return connection;
    }*/
}
