package by.edik.car_api.dao;

import by.edik.car_api.dao.exception.DaoSqlException;
import by.edik.car_api.dao.model.Ad;
import by.edik.car_api.dao.model.Condition;
import by.edik.car_api.web.dto.AdFullInformationDto;
import by.edik.car_api.web.dto.AdShortInformationDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdDao extends AbstractDao<Ad> {

    private static volatile AdDao adDaoInstance;

    private static final String GET_ALL_QUERY = "SELECT * FROM ads";
    private static final String GET_ALL_SHORT_INFO_QUERY = "SELECT " +
        "ads.ad_id, " +
        "ads.year, " +
        "ads.brand, " +
        "ads.model, " +
        "ads.condition, " +
        "ads.mileage, " +
        "ads.creation_time, " +
        "ui.name, " +
        "COUNT(p.reference) as pics " +
        "FROM ads " +
        "INNER JOIN user_information ui on ads.user_id = ui.user_id " +
        "LEFT JOIN pictures p on ads.ad_id = p.ad_id " +
        "GROUP BY ads.ad_id, ui.name, ads.creation_time " +
        "ORDER BY ads.creation_time DESC " +
        "LIMIT ? OFFSET ?";
    private static final String GET_BY_ID_FULL_INFO_QUERY = "SELECT " +
        "ads.ad_id, " +
        "ads.year, " +
        "ads.brand, " +
        "ads.model, " +
        "ads.engine_volume, " +
        "ads.engine_power, " +
        "ads.condition, " +
        "ads.mileage, " +
        "ui.name, " +
        "up.phone_number, " +
        "ads.creation_time, " +
        "ads.editing_time " +
        "FROM ads " +
        "INNER JOIN user_information ui on ads.user_id = ui.user_id " +
        "LEFT JOIN user_phones up on ui.user_id = up.user_id " +
        "WHERE ads.ad_id = ?";
    private static final String GET_ALL_PICTURES_BY_ID_QUERY = "SELECT reference " +
        "FROM pictures " +
        "WHERE ad_id = ?";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM ads WHERE ad_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO ads " +
        "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?::\"conditions\", ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE ads SET (" +
        "year, brand, model, engine_volume, condition, mileage, engine_power, creation_time, editing_time) " +
        "= (?, ?, ?, ?, ?::\"conditions\", ?, ?, ?) " +
        "WHERE ad_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM ads WHERE ad_id = ?";
    private static final String UPDATE_EDITING_TIME_BY_PICTURE_ID_QUERY = "UPDATE ads SET editing_time = ? " +
        "WHERE ad_id = (" +
        "SELECT ad_id " +
        "FROM pictures " +
        "WHERE picture_id = ?)";
    private static final String UPDATE_ALLOWED_FIELDS_QUERY = "UPDATE ads SET (" +
        "year, brand, model, engine_volume, mileage, engine_power, editing_time) " +
        "= (?, ?, ?, ?, ?, ?, ?) " +
        "WHERE ad_id = ?";

    @Override
    public Ad create(Ad ad) {
        long key = -1L;
        ResultSet resultSet;

        try {
            PreparedStatement preparedStatement = prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, ad.getUserId());
            preparedStatement.setInt(2, ad.getYear());
            preparedStatement.setString(3, ad.getBrand());
            preparedStatement.setString(4, ad.getModel());
            preparedStatement.setInt(5, ad.getEngineVolume());
            preparedStatement.setString(6, ad.getCondition().name().toLowerCase());
            preparedStatement.setLong(7, ad.getMileage());
            preparedStatement.setInt(8, ad.getEnginePower());
            preparedStatement.setObject(9, LocalDateTime.now());
            preparedStatement.setObject(10, LocalDateTime.now());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong("ad_id");
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        close(resultSet);

        return ad.setAdId(key);
    }

    @Override
    public Ad getById(Long id) {
        ResultSet resultSet;
        Ad ad = null;

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ad = buildAdFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        close(resultSet);

        return ad;
    }

    @Override
    public List<Ad> getAll() {
        ResultSet resultSet;
        List<Ad> ads = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_ALL_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ads.add(buildAdFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        close(resultSet);

        return ads;
    }

    @Override
    public void update(Ad ad) {
        try {
            PreparedStatement preparedStatement = prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, ad.getYear());
            preparedStatement.setString(2, ad.getBrand());
            preparedStatement.setString(3, ad.getModel());
            preparedStatement.setInt(4, ad.getEngineVolume());
            preparedStatement.setString(5, ad.getCondition().name().toLowerCase());
            preparedStatement.setLong(6, ad.getMileage());
            preparedStatement.setInt(7, ad.getEnginePower());
            preparedStatement.setObject(8, LocalDateTime.now());
            preparedStatement.setLong(9, ad.getAdId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement preparedStatement = prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    public List<AdShortInformationDto> getAllShortInformationAds(int pageNumber, int limit) {
        ResultSet resultSet;
        List<AdShortInformationDto> ads = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_ALL_SHORT_INFO_QUERY);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, (pageNumber - 1) * limit);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ads.add(AdShortInformationDto.builder()
                    .adId(resultSet.getLong("ad_id"))
                    .year(resultSet.getInt("year"))
                    .brand(resultSet.getString("brand"))
                    .model(resultSet.getString("model"))
                    .condition(Condition.valueOf(resultSet.getString("condition").toUpperCase()))
                    .mileage(resultSet.getLong("mileage"))
                    .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                    .ownerName(resultSet.getString("name"))
                    .picturesNumber(resultSet.getInt("pics"))
                    .build());
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        close(resultSet);

        return ads;
    }

    public AdFullInformationDto getFullInformationAdById(long id) {
        ResultSet resultSet;
        AdFullInformationDto adFullInformationDto = null;

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_BY_ID_FULL_INFO_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            List<String> phones = new ArrayList<>();

            if (resultSet.next()) {
                phones.add(resultSet.getString("phone_number"));
                adFullInformationDto = AdFullInformationDto.builder()
                    .adId(resultSet.getLong("ad_id"))
                    .year(resultSet.getInt("year"))
                    .brand(resultSet.getString("brand"))
                    .model(resultSet.getString("model"))
                    .engineVolume(resultSet.getInt("engine_volume"))
                    .enginePower(resultSet.getInt("engine_power"))
                    .condition(Condition.valueOf(resultSet.getString("condition").toUpperCase()))
                    .mileage(resultSet.getLong("mileage"))
                    .ownerName(resultSet.getString("name"))
                    .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                    .editingTime(resultSet.getTimestamp("editing_time").toLocalDateTime())
                    .build();
            }

            while (resultSet.next()) {
                phones.add(resultSet.getString("phone_number"));
            }

            if (adFullInformationDto != null) {
                adFullInformationDto.setOwnerPhoneNumbers(phones);
                adFullInformationDto.setPictureReferences(getAllPicturesByAdId(adFullInformationDto.getAdId()));
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        close(resultSet);

        return adFullInformationDto;
    }

    public List<String> getAllPicturesByAdId(Long id) {
        ResultSet resultSet;
        List<String> pictureReferences = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_ALL_PICTURES_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pictureReferences.add(resultSet.getString("reference"));
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        close(resultSet);

        return pictureReferences;
    }

    public void updateAdEditingTimeByPictureId(Long id) {
        try {
            PreparedStatement preparedStatement = prepareStatement(UPDATE_EDITING_TIME_BY_PICTURE_ID_QUERY);
            preparedStatement.setObject(1, LocalDateTime.now());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    public void updateAllowedFields(Ad ad) {
        try {
            PreparedStatement preparedStatement = prepareStatement(UPDATE_ALLOWED_FIELDS_QUERY);
            preparedStatement.setInt(1, ad.getYear());
            preparedStatement.setString(2, ad.getBrand());
            preparedStatement.setString(3, ad.getModel());
            preparedStatement.setInt(4, ad.getEngineVolume());
            preparedStatement.setLong(5, ad.getMileage());
            preparedStatement.setInt(6, ad.getEnginePower());
            preparedStatement.setObject(7, LocalDateTime.now());
            preparedStatement.setLong(8, ad.getAdId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    private static Ad buildAdFromResultSet(ResultSet resultSet) {
        Ad ad;

        try {
            ad = Ad.builder()
                .adId(resultSet.getLong("ad_id"))
                .userId(resultSet.getLong("user_id"))
                .year(resultSet.getInt("year"))
                .brand(resultSet.getString("brand"))
                .model(resultSet.getString("model"))
                .engineVolume(resultSet.getInt("engine_volume"))
                .condition(Condition.valueOf(resultSet.getString("condition").toUpperCase()))
                .mileage(resultSet.getLong("mileage"))
                .enginePower(resultSet.getInt("engine_power"))
                .creationTime(resultSet.getTimestamp("creation_time").toLocalDateTime())
                .editingTime(resultSet.getTimestamp("editing_time").toLocalDateTime())
                .build();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        return ad;
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
