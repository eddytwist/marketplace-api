package by.edik.car_api.service.impl;

import by.edik.car_api.dao.UserDao;
import by.edik.car_api.dao.model.User;
import by.edik.car_api.service.AbstractService;
import by.edik.car_api.service.UserService;
import by.edik.car_api.service.exception.ServiceFailedException;
import by.edik.car_api.web.dto.request.CreateUserRequest;
import by.edik.car_api.web.dto.request.UpdateUserRequest;
import by.edik.car_api.web.dto.response.UserResponse;
import by.edik.car_api.web.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserServiceImpl extends AbstractService implements UserService {

    private static volatile UserServiceImpl userServiceInstance;

    private final UserDao userDao = UserDao.getInstance();

    @Override
    public UserResponse create(CreateUserRequest createUserRequest) {
        User userToCreate = UserMapper.toUser(createUserRequest);
        User createdUser;

        try {
            startTransaction();
            createdUser = userDao.create(userToCreate);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + createUserRequest, e);
        }

        return UserMapper.toUserResponse(createdUser);
    }

    @Override
    public UserResponse getById(Long id) {
        User user;

        try {
            startTransaction();
            user = userDao.getById(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find User with id: " + id, e);
        }

        return UserMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users;

        try {
            startTransaction();
            users = userDao.getAll();
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Users.", e);
        }

        return users.stream()
            .map(UserMapper::toUserResponse)
            .collect(Collectors.toList());
    }

    @Override
    public UserResponse update(UpdateUserRequest updateUserRequest) {
        User user = UserMapper.toUser(updateUserRequest);

        try {
            startTransaction();
            userDao.update(user);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't update User: " + updateUserRequest, e);
        }

        return getById(user.getUserId());
    }

    @Override
    public void delete(Long id) {
        try {
            startTransaction();
            userDao.delete(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't delete User id: " + id, e);
        }
    }

    public static UserServiceImpl getInstance() {
        UserServiceImpl localInstance = userServiceInstance;

        if (localInstance == null) {

            synchronized (UserServiceImpl.class) {
                localInstance = userServiceInstance;

                if (localInstance == null) {
                    userServiceInstance = localInstance = new UserServiceImpl();
                }
            }
        }

        return localInstance;
    }
}
