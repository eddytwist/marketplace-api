package by.edik.car_api.dao;

import by.edik.car_api.dao.db.ConnectionManager;
import by.edik.car_api.dao.exception.DaoSqlException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDao<T> implements GenericDao<T> {
    protected static PreparedStatement prepareStatement(String query) throws SQLException {
        return ConnectionManager.getConnection().prepareStatement(query);
    }

    protected static PreparedStatement prepareStatement(String query, int flag) throws SQLException {
        return ConnectionManager.getConnection().prepareStatement(query, flag);
    }

    protected static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DaoSqlException("Closing ResultSet failed.", e);
            }
        }
    }
}

