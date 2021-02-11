package by.edik.car_api.dao;

import by.edik.car_api.model.UserInformation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInformationDao extends AbstractDao<UserInformation> {

    private static volatile UserInformationDao userInformationDaoInstance;

    @Override
    public UserInformation create(UserInformation userInformation) {
        return null;
    }

    @Override
    public UserInformation getById(long id) {
        return null;
    }

    @Override
    public List<UserInformation> getAll() {
        return null;
    }

    @Override
    public void update(UserInformation userInformation) {

    }

    @Override
    public void delete(long id) {

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
