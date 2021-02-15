package by.edik.car_api.dao;

import by.edik.car_api.dao.exception.DaoSqlException;
import by.edik.car_api.model.Ad;
import by.edik.car_api.model.Condition;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdDao extends AbstractDao<Ad> {

    private static volatile AdDao adDaoInstance;

    private static final String GET_ALL_QUERY = "SELECT * FROM ads";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM ads WHERE ad_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO ads " +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?::\"conditions\", ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE ads SET (" +
            "year, brand, model, engine_volume, condition, mileage, engine_power, creation_time, editing_time) " +
            "= (?, ?, ?, ?, ?::\"conditions\", ?, ?, ?, ?) " +
            "WHERE ad_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM ads WHERE ad_id = ?";
    private static final String UPDATE_ALLOWED_FIELDS_QUERY = "UPDATE ads SET (" +
            "year, brand, model, engine_volume, mileage, engine_power) " +
            "= (?, ?, ?, ?, ?, ?) " +
            "WHERE ad_id = ?";


    @Override
    public Ad create(Ad ad) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        long key = -1L;
        try {
            preparedStatement = prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, ad.getUserId());
            preparedStatement.setInt(2, ad.getYear());
            preparedStatement.setString(3, ad.getBrand());
            preparedStatement.setString(4, ad.getModel());
            preparedStatement.setInt(5, ad.getEngineVolume());
            preparedStatement.setString(6, ad.getCondition().name().toLowerCase());
            preparedStatement.setLong(7, ad.getMileage());
            preparedStatement.setInt(8, ad.getEnginePower());
            preparedStatement.setObject(9, ad.getCreationTime());
            preparedStatement.setObject(10, ad.getEditingTime());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong("ad_id");
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        return ad.setAdId(key);
    }

    @Override
    public Ad getById(long id) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        Ad ad = null;
        try {
            preparedStatement = prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ad = new Ad(
                        resultSet.getLong("ad_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getInt("year"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("engine_volume"),
                        Condition.valueOf(resultSet.getString("condition").toUpperCase()),
                        resultSet.getLong("mileage"),
                        resultSet.getInt("engine_power"),
                        resultSet.getTimestamp("creation_time").toLocalDateTime(),
                        resultSet.getTimestamp("editing_time").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        close(resultSet);
        return ad;
    }

    @Override
    public List<Ad> getAll() {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        List<Ad> ads = new ArrayList<>();
        try {
            preparedStatement = prepareStatement(GET_ALL_QUERY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ads.add(new Ad(
                        resultSet.getLong("ad_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getInt("year"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("engine_volume"),
                        Condition.valueOf(resultSet.getString("condition").toUpperCase()),
                        resultSet.getLong("mileage"),
                        resultSet.getInt("engine_power"),
                        resultSet.getTimestamp("creation_time").toLocalDateTime(),
                        resultSet.getTimestamp("editing_time").toLocalDateTime())
                );
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        close(resultSet);
        return ads;
    }

    @Override
    public void update(Ad ad) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, ad.getYear());
            preparedStatement.setString(2, ad.getBrand());
            preparedStatement.setString(3, ad.getModel());
            preparedStatement.setInt(4, ad.getEngineVolume());
            preparedStatement.setString(5, ad.getCondition().name().toLowerCase());
            preparedStatement.setLong(6, ad.getMileage());
            preparedStatement.setInt(7, ad.getEnginePower());
            preparedStatement.setObject(8, ad.getCreationTime());
            preparedStatement.setObject(9, ad.getEditingTime());
            preparedStatement.setLong(10, ad.getAdId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public void delete(long id) {
        PictureDao pictureDao = PictureDao.getInstance();
        pictureDao.delete(id);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    public void updateAllowedFields(Ad ad) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(UPDATE_ALLOWED_FIELDS_QUERY);
            preparedStatement.setInt(1, ad.getYear());
            preparedStatement.setString(2, ad.getBrand());
            preparedStatement.setString(3, ad.getModel());
            preparedStatement.setInt(4, ad.getEngineVolume());
            preparedStatement.setLong(5, ad.getMileage());
            preparedStatement.setInt(6, ad.getEnginePower());
            preparedStatement.setLong(7, ad.getAdId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    public static AdDao getInstance() {
        AdDao localInstance = adDaoInstance;
        if (localInstance == null) {
            synchronized (AdDao.class) {
                localInstance = adDaoInstance;
                if (localInstance == null) {
                    adDaoInstance = localInstance = new AdDao();
                }
            }
        }
        return localInstance;
    }

}
