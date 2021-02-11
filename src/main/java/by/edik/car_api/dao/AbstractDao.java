package by.edik.car_api.dao;

import by.edik.car_api.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDao<T> implements GenericDao<T>  {
    protected PreparedStatement prepareStatement(String query) throws SQLException {
        return ConnectionManager.getConnection().prepareStatement(query);
    }

    protected PreparedStatement prepareStatement(String query, int flag) throws SQLException {
        return ConnectionManager.getConnection().prepareStatement(query, flag);
    }

    protected void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

