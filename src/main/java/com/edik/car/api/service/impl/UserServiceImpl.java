package com.edik.car.api.service.impl;

import com.edik.car.api.dao.UserDao;
import com.edik.car.api.dao.model.User;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.UserService;
import com.edik.car.api.service.exception.ServiceEntityNotFoundException;
import com.edik.car.api.service.exception.ServiceFailedException;
import com.edik.car.api.web.dto.request.CreateUserRequest;
import com.edik.car.api.web.dto.request.UpdateUserRequest;
import com.edik.car.api.web.dto.response.UserResponse;
import com.edik.car.api.web.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
            begin();
            createdUser = userDao.save(userToCreate);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + createUserRequest, e);
        }

        return UserMapper.toUserResponse(createdUser);
    }

    @Override
    public UserResponse getById(Long id) {
        User user;

        try {
            begin();
            user = userDao.findById(id);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find User with id: " + id, e);
        }

        return UserMapper.toUserResponse(user);
    }

    public User getByIdForAd(Long id) {
        User user;

        try {
            begin();
            user = userDao.getUserToResponse(id);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find User with id: " + id, e);
        }

        return user;
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users;

        try {
            begin();
            users = userDao.findAll();
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Users.", e);
        }

        return users.stream()
            .map(UserMapper::toUserResponse)
            .collect(Collectors.toList());
    }

    @Override
    public UserResponse update(UpdateUserRequest updateUserRequest) {
        User updatedUser;

        try {
            begin();

            User foundedUser = userDao.findById(updateUserRequest.getUserId());

            if (foundedUser != null) {
                UserMapper.updateUserFields(foundedUser, updateUserRequest);
                updatedUser = userDao.update(foundedUser);
            } else {
                throw new ServiceEntityNotFoundException("User", updateUserRequest.getUserId());
            }
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't update User: " + updateUserRequest, e);
        }

        return UserMapper.toUserResponse(updatedUser);
    }

    @Override
    public void delete(Long id) {
        try {
            begin();
            userDao.delete(id);
            commit();
        } catch (Exception e) {
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
