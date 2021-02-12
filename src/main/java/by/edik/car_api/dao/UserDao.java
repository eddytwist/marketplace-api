package by.edik.car_api.dao;

import by.edik.car_api.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao extends AbstractDao<User> {

    private static volatile UserDao userDaoInstance;

    private final String getAllQuery = "SELECT * FROM ads";
    private final String getByIdQuery = "SELECT * FROM ads WHERE ad_id = ?";
    private final String createQuery = "INSERT INTO users " +
            "VALUES (DEFAULT, ?, ?, ?)";
    private final String updateQuery = "UPDATE ads SET (year, brand, model, engine_volume, mileage, engine_power) " +
            "= (?, ?, ?, ?, ?, ?) " +
            "WHERE ad_id = ?";
    private final String deleteQuery = "DELETE FROM ads WHERE ad_id = ?";

    @Override
    public User create(User user) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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
