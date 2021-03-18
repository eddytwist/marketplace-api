package com.edik.car.api.service.impl;

import com.edik.car.api.dao.UserDaoHiba;
import com.edik.car.api.dao.model.User;
import com.edik.car.api.service.AbstractServiceHiba;
import com.edik.car.api.service.UserService;
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
public final class UserServiceImplHiba extends AbstractServiceHiba implements UserService {

    private static volatile UserServiceImplHiba userServiceInstance;

    private final UserDaoHiba userDaoHiba = UserDaoHiba.getInstance();

    @Override
    public UserResponse create(CreateUserRequest createUserRequest) {
        User userToCreate = UserMapper.toUser(createUserRequest);
        User createdUser;

        try {
            begin();
            createdUser = userDaoHiba.save(userToCreate);
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
            user = userDaoHiba.findById(id);
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
            user = userDaoHiba.findById(id);
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
            users = userDaoHiba.findAll();
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
        User user = UserMapper.toUser(updateUserRequest);

        try {
            begin();
            userDaoHiba.update(user);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't update User: " + updateUserRequest, e);
        }

        return getById(user.getUserId());
    }

    @Override
    public void delete(Long id) {
        try {
            begin();
            userDaoHiba.delete(id);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't delete User id: " + id, e);
        }
    }

    public static UserServiceImplHiba getInstance() {
        UserServiceImplHiba localInstance = userServiceInstance;

        if (localInstance == null) {

            synchronized (UserServiceImplHiba.class) {
                localInstance = userServiceInstance;

                if (localInstance == null) {
                    userServiceInstance = localInstance = new UserServiceImplHiba();
                }
            }
        }

        return localInstance;
    }
}
