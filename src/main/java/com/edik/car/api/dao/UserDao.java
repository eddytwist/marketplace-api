package com.edik.car.api.dao;

import com.edik.car.api.dao.exception.DaoSqlException;
import com.edik.car.api.dao.exception.ObjectNotFoundException;
import com.edik.car.api.dao.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserDao extends AbstractDao<User> {

    private static volatile UserDao userDaoInstance;

    private static final String GET_ALL_QUERY = "SELECT * FROM users";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET (username, email, password) = (?, ?, ?) WHERE user_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE user_id = ?";

    @Override
    public Class<User> getEntityType() {
        return null;
    }

    @Override
    public User save(User user) {
        ResultSet resultSet;
        long key = -1L;

        try {
            PreparedStatement preparedStatement = prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong("user_id");
            }
        } catch (SQLException e) {
            throw new DaoSqlException("SQL failed.", e);
        }

        close(resultSet);

        return user.setUserId(key);
    }

    @Override
    public List<User> findAll() {
        ResultSet resultSet;
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_ALL_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(buildUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSqlException("SQL failed.", e);
        }

        close(resultSet);

        return users;
    }

    @Override
    public User update(User user) {
        try {
            PreparedStatement preparedStatement = prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException("SQL failed.", e);
        }

        return user;
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

    @Override
    public User findById(Long id) {
        ResultSet resultSet;
        User user;

        try {
            PreparedStatement preparedStatement = prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = buildUserFromResultSet(resultSet);
            } else {
                throw new ObjectNotFoundException("User", id);
            }
        } catch (SQLException e) {
            throw new DaoSqlException("SQL failed.", e);
        }

        close(resultSet);

        return user;
    }

    private static User buildUserFromResultSet(ResultSet resultSet) {
        User user;

        try {
            user = User.builder()
                .userId(resultSet.getLong("user_id"))
                .username(resultSet.getString("username"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .build();
        } catch (SQLException e) {
            throw new DaoSqlException("Troubles with getting params from ResultSet.", e);
        }

        return user;
    }

    public static UserDao getInstance() {
        UserDao localInstance = userDaoInstance;

        if (localInstance == null) {

            synchronized (UserDao.class) {
                localInstance = userDaoInstance;

                if (localInstance == null) {
                    userDaoInstance = localInstance = new UserDao();
                }
            }
        }

        return localInstance;
    }
}
