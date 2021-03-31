package com.edik.car.api.service.impl;

import com.edik.car.api.dao.model.User;
import com.edik.car.api.repository.UserRepository;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.UserService;
import com.edik.car.api.service.exception.ServiceEntityNotFoundException;
import com.edik.car.api.web.dto.request.CreateUserRequest;
import com.edik.car.api.web.dto.request.UpdateUserRequest;
import com.edik.car.api.web.dto.response.UserResponse;
import com.edik.car.api.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractService implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse create(CreateUserRequest createUserRequest) {
        User userToCreate = UserMapper.toUser(createUserRequest);

        User createdUser = userRepository.save(userToCreate);

        return UserMapper.toUserResponse(createdUser);
    }

    @Override
    @Transactional
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public List<UserResponse> getAll() {
        return userRepository.findAll()
            .stream()
            .map(UserMapper::toUserResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponse update(UpdateUserRequest updateUserRequest) {
        User foundedUser = userRepository.findById(updateUserRequest.getUserId())
            .orElseThrow(() -> new ServiceEntityNotFoundException("User", updateUserRequest.getUserId()));

        UserMapper.updateUserFields(foundedUser, updateUserRequest);

        User updatedUser = userRepository.save(foundedUser);

        return UserMapper.toUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
