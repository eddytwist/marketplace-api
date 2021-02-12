package by.edik.car_api.dao;

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

    private final String getAllQuery = "SELECT * FROM ads";
    private final String getByIdQuery = "SELECT * FROM ads WHERE ad_id = ?";
    private final String createQuery = "INSERT INTO ads " +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?::\"conditions\", ?, ?, ?, ?)";
    private final String updateQuery = "UPDATE ads SET (year, brand, model, engine_volume, mileage, engine_power) " +
            "= (?, ?, ?, ?, ?, ?) " +
            "WHERE ad_id = ?";
    private final String deleteQuery = "DELETE FROM ads WHERE ad_id = ?";


    @Override
    public Ad create(Ad ad) {
        PreparedStatement preparedStatement;
        ResultSet keys = null;
        try {
            preparedStatement = prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
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
            keys = preparedStatement.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ad.setAdId(keys);
    }

    @Override
    public Ad getById(long id) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        Ad ad = null;
        try {
            preparedStatement = prepareStatement(getByIdQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ad = new Ad(
                        resultSet.getLong("ad_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getShort("year"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getShort("engine_volume"),
                        Condition.valueOf(resultSet.getString("condition").toUpperCase()),
                        resultSet.getLong("mileage"),
                        resultSet.getInt("engine_power"),
                        resultSet.getTimestamp("creation_time").toLocalDateTime(),
                        resultSet.getTimestamp("editing_time").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            preparedStatement = prepareStatement(getAllQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ads.add(new Ad(
                        resultSet.getLong("ad_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getShort("year"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getShort("engine_volume"),
                        Condition.valueOf(resultSet.getString("condition").toUpperCase()),
                        resultSet.getLong("mileage"),
                        resultSet.getInt("engine_power"),
                        resultSet.getTimestamp("creation_time").toLocalDateTime(),
                        resultSet.getTimestamp("editing_time").toLocalDateTime())
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(resultSet);
        return ads;
    }

    @Override
    public void update(Ad ad) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ad.getYear());
            preparedStatement.setString(2, ad.getBrand());
            preparedStatement.setString(3, ad.getModel());
            preparedStatement.setInt(4, ad.getEngineVolume());
            preparedStatement.setLong(5, ad.getMileage());
            preparedStatement.setInt(6, ad.getEnginePower());
            preparedStatement.setLong(7, ad.getAdId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        PictureDao pictureDao = PictureDao.getInstance();
        pictureDao.delete(id);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(deleteQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        AdDao adDao = new AdDao();
        Ad ad = adDao.getById(2);
        Ad newAd = adDao.create(ad);
        System.out.println(newAd);
        newAd.setBrand("DJIGULI");
        adDao.update(newAd);
        adDao.delete(1);
        List<Ad> all = adDao.getAll();
        System.out.println(all);
    }

}
