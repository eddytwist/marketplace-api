package by.edik.car_api.dao;

import by.edik.car_api.dao.exception.DaoSqlException;
import by.edik.car_api.model.Picture;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PictureDao extends AbstractDao<Picture> {

    private static volatile PictureDao pictureDaoInstance;

    private static final String GET_ALL_QUERY = "SELECT * FROM pictures";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM pictures WHERE picture_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO pictures " +
            "VALUES (DEFAULT, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE pictures SET reference = ? " +
            "WHERE picture_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM pictures WHERE picture_id = ?";

    @Override
    public Picture create(Picture picture) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        long key = -1L;
        try {
            preparedStatement = prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, picture.getReference());
            preparedStatement.setLong(2, picture.getAdId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong("picture_id");
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        return picture.setPictureId(key);
    }

    @Override
    public Picture getById(Long id) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Picture picture = null;
        try {
            preparedStatement = prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                picture = new Picture(
                        resultSet.getLong("picture_id"),
                        resultSet.getString("reference"),
                        resultSet.getLong("ad_id")
                );
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        close(resultSet);
        return picture;
    }

    @Override
    public List<Picture> getAll() {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Picture> pictures = new ArrayList<>();
        try {
            preparedStatement = prepareStatement(GET_ALL_QUERY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pictures.add(new Picture(
                        resultSet.getLong("picture_id"),
                        resultSet.getString("reference"),
                        resultSet.getLong("ad_id"))
                );
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        close(resultSet);
        return pictures;
    }

    @Override
    public void update(Picture picture) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, picture.getReference());
            preparedStatement.setLong(2, picture.getPictureId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    public static PictureDao getInstance() {
        PictureDao localInstance = pictureDaoInstance;
        if (localInstance == null) {
            synchronized (PictureDao.class) {
                localInstance = pictureDaoInstance;
                if (localInstance == null) {
                    pictureDaoInstance = localInstance = new PictureDao();
                }
            }
        }
        return localInstance;
    }
}
