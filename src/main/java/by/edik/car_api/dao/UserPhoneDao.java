package by.edik.car_api.dao;

import by.edik.car_api.dao.exception.DaoSqlException;
import by.edik.car_api.dao.exception.ObjectNotFoundException;
import by.edik.car_api.dao.model.UserPhone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserPhoneDao extends AbstractDao<UserPhone> {

    private static volatile UserPhoneDao userPhoneDaoInstance;

    private static final String GET_ALL_QUERY = "SELECT * FROM user_phones";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM user_phones WHERE phone_number_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO user_phones VALUES (DEFAULT, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE user_phones SET phone_number = ? WHERE phone_number_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user_phones WHERE phone_number_id = ?";

    @Override
    public UserPhone save(UserPhone userPhone) {
        ResultSet resultSet;
        long key = -1L;

        try {
            PreparedStatement preparedStatement = prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userPhone.getPhoneNumber());
//            preparedStatement.setLong(2, userPhone.getUserId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong("phone_number_id");
            }
        } catch (SQLException e) {
            throw new DaoSqlException("SQL failed.", e);
        }

        close(resultSet);

        return userPhone.setPhoneNumberId(key);
    }

    @Override
    public UserPhone findById(Long id) {
        ResultSet resultSet;
        UserPhone userPhone;

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userPhone = buildUserPhoneFromResultSet(resultSet);
            } else {
                throw new ObjectNotFoundException("UserPhone", id);
            }
        } catch (SQLException e) {
            throw new DaoSqlException("SQL failed.", e);
        }

        close(resultSet);

        return userPhone;
    }

    @Override
    public List<UserPhone> findAll() {
        ResultSet resultSet;
        List<UserPhone> usersPhones = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_ALL_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                usersPhones.add(buildUserPhoneFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("SQL failed.", e);
        }

        close(resultSet);

        return usersPhones;
    }

    @Override
    public void update(UserPhone userPhone) {
        try {
            PreparedStatement preparedStatement = prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, userPhone.getPhoneNumber());
            preparedStatement.setLong(2, userPhone.getPhoneNumberId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException("SQL failed.", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement preparedStatement = prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException("SQL failed.", e);
        }
    }

    private static UserPhone buildUserPhoneFromResultSet(ResultSet resultSet) {
        UserPhone userPhone;

        try {
            userPhone = UserPhone.builder()
                .phoneNumberId(resultSet.getLong("phone_number_id"))
                .phoneNumber(resultSet.getString("phone_number"))
//                .userId(resultSet.getLong("user_id"))
                .build();
        } catch (SQLException e) {
            throw new DaoSqlException("Troubles with getting params from ResultSet.", e);
        }

        return userPhone;
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
