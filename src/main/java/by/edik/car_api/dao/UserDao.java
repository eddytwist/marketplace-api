package by.edik.car_api.dao;

import by.edik.car_api.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao extends AbstractDao<User> {

    private static volatile UserDao userDaoInstance;

    private final String getAllQuery = "SELECT * FROM users";
    private final String getByIdQuery = "SELECT * FROM users WHERE user_id = ?";
    private final String createQuery = "INSERT INTO users " +
            "VALUES (DEFAULT, ?, ?, ?)";
    private final String updateQuery = "UPDATE users SET (username, email, password) " +
            "= (?, ?, ?) " +
            "WHERE user_id = ?";
    private final String deleteQuery = "DELETE FROM users WHERE user_id = ?";

    @Override
    public User create(User user) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        long key = -1L;
        try {
            preparedStatement = prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                key = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user.setUserId(key);
    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(long id) {
        AdDao adDao = AdDao.getInstance();
        adDao.delete(id);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(deleteQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
