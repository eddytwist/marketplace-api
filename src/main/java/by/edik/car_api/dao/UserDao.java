package by.edik.car_api.dao;

import by.edik.car_api.dao.exception.DaoSqlException;
import by.edik.car_api.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao extends AbstractDao<User> {

    private static volatile UserDao userDaoInstance;

    private static final String GET_ALL_QUERY = "SELECT * FROM users";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO users " +
            "VALUES (DEFAULT, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET (username, email, password) " +
            "= (?, ?, ?) " +
            "WHERE user_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE user_id = ?";

    @Override
    public User create(User user) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        long key = -1L;
        try {
            preparedStatement = prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong("user_id");
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        return user.setUserId(key);
    }

    @Override
    public User getById(long id) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        User user = null;
        try {
            preparedStatement = prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        close(resultSet);
        return user;
    }

    @Override
    public List<User> getAll() {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = prepareStatement(GET_ALL_QUERY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"))
                );
            }
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
        close(resultSet);
        return users;
    }

    @Override
    public void update(User user) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
    }

    @Override
    public void delete(long id) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSqlException(e);
        }
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
