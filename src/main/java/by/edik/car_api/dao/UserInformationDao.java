package by.edik.car_api.dao;

import by.edik.car_api.dao.exception.DaoSqlException;
import by.edik.car_api.dao.model.UserInformation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserInformationDao extends AbstractDao<UserInformation> {

    private static volatile UserInformationDao userInformationDaoInstance;

    private static final String GET_ALL_QUERY = "SELECT * FROM user_information";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM user_information WHERE user_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO user_information VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE user_information SET name = ? WHERE user_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user_information WHERE user_id = ?";

    @Override
    public UserInformation create(UserInformation userInformation) {

        try {
            PreparedStatement preparedStatement = prepareStatement(CREATE_QUERY);
            preparedStatement.setLong(1, userInformation.getUserId());
            preparedStatement.setString(2, userInformation.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        return userInformation;
    }

    @Override
    public UserInformation getById(Long id) {
        ResultSet resultSet;
        UserInformation userInformation = null;

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userInformation = buildUserInformationFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        close(resultSet);

        return userInformation;
    }

    @Override
    public List<UserInformation> getAll() {
        ResultSet resultSet;
        List<UserInformation> usersInformation = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_ALL_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                usersInformation.add(buildUserInformationFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        close(resultSet);

        return usersInformation;
    }

    @Override
    public void update(UserInformation userInformation) {
        try {
            PreparedStatement preparedStatement = prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, userInformation.getName());
            preparedStatement.setLong(2, userInformation.getUserId());
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

    private static UserInformation buildUserInformationFromResultSet(ResultSet resultSet) {
        UserInformation userInformation;

        try {
            userInformation = UserInformation.builder()
                .userId(resultSet.getLong("user_id"))
                .name(resultSet.getString("name"))
                .build();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }

        return userInformation;
    }

    public static UserInformationDao getInstance() {
        UserInformationDao localInstance = userInformationDaoInstance;

        if (localInstance == null) {

            synchronized (UserInformationDao.class) {
                localInstance = userInformationDaoInstance;

                if (localInstance == null) {
                    userInformationDaoInstance = localInstance = new UserInformationDao();
                }
            }
        }

        return localInstance;
    }
}
