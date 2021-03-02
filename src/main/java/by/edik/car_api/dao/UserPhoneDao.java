package by.edik.car_api.dao;

import by.edik.car_api.dao.exception.DaoSqlException;
import by.edik.car_api.model.UserPhone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPhoneDao extends AbstractDao<UserPhone> {

    private static volatile UserPhoneDao userPhoneDaoInstance;

    private static final String GET_ALL_QUERY = "SELECT * FROM user_phones";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM user_phones WHERE phone_number_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO user_phones " +
            "VALUES (DEFAULT, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE user_phones SET phone_number = ? " +
            "WHERE phone_number_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user_phones WHERE phone_number_id = ?";

    @Override
    public UserPhone create(UserPhone userPhone) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        long key = -1L;
        try {
            preparedStatement = prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userPhone.getPhoneNumber());
            preparedStatement.setLong(2, userPhone.getUserId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong("phone_number_id");
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        return userPhone.setPhoneNumberId(key);
    }

    @Override
    public UserPhone getById(Long id) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        UserPhone userPhone = null;
        try {
            preparedStatement = prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userPhone = new UserPhone(
                        resultSet.getLong("phone_number_id"),
                        resultSet.getString("phone_number"),
                        resultSet.getLong("user_id")
                );
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        close(resultSet);
        return userPhone;
    }

    @Override
    public List<UserPhone> getAll() {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<UserPhone> usersPhones = new ArrayList<>();
        try {
            preparedStatement = prepareStatement(GET_ALL_QUERY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                usersPhones.add(new UserPhone(
                        resultSet.getLong("phone_number_id"),
                        resultSet.getString("phone_number"),
                        resultSet.getLong("user_id"))
                );
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        close(resultSet);
        return usersPhones;
    }

    @Override
    public void update(UserPhone userPhone) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, userPhone.getPhoneNumber());
            preparedStatement.setLong(2, userPhone.getPhoneNumberId());
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

    public static UserPhoneDao getInstance() {
        UserPhoneDao localInstance = userPhoneDaoInstance;
        if (localInstance == null) {
            synchronized (UserPhoneDao.class) {
                localInstance = userPhoneDaoInstance;
                if (localInstance == null) {
                    userPhoneDaoInstance = localInstance = new UserPhoneDao();
                }
            }
        }
        return localInstance;
    }
}
