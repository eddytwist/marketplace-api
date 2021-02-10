package by.edik.car_api.db;

import by.edik.car_api.db.exception.DbManagerException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDao {
    protected PreparedStatement prepareStatement(String query) throws SQLException, DbManagerException {
        return ConnectionManager.getConnection().prepareStatement(query);
    }

    protected PreparedStatement prepareStatement(String query, int flag) throws SQLException, DbManagerException {
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

