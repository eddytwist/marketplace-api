package by.edik.car_api.dao;

import by.edik.car_api.model.Ad;
import by.edik.car_api.model.Condition;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdDao extends AbstractDao<Ad> {

    private static volatile AdDao adDaoInstance;

    private String getAllQuery = "SELECT * FROM ads";


    @Override
    @SneakyThrows
    public Ad create(Ad ad) {
        PreparedStatement psGet = prepareStatement(getAllQuery);
//        psGet.setLong(1, (long) t);
//        ResultSet rs = psGet.executeQuery();
//        Ad ad = null;
//
//        if (rs.next()) {
//            ad = new Ad(rs.getLong(1), rs.getLong(2), rs.getLong(3),
//                    rs.getString(4), rs.getTimestamp(5));
//        }
//
//        close(rs);

        return ad;
    }

    @Override
    public Ad getById(long id) {
        return null;
    }

    @Override
    @SneakyThrows
    public List<Ad> getAll() {
        PreparedStatement preparedStatement = prepareStatement(getAllQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        List <Ad> ads = new ArrayList<>();
        while(resultSet.next()) {
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
                    resultSet.getDate("creation_time"),
                    resultSet.getDate("creation_time"))
            );
        }
        close(resultSet);
        return ads;
    }

    @Override
    public void update(Ad ad) {

    }

    @Override
    public void delete(long id) {

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
        AdDao ad = new AdDao();
        List<Ad> all = ad.getAll();
        all.forEach(System.out::println);
        System.out.println(all);
    }

}
