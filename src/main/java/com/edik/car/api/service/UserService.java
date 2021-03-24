package com.edik.car.api.service;

import com.edik.car.api.web.dto.request.CreateUserRequest;
import com.edik.car.api.web.dto.request.UpdateUserRequest;
import com.edik.car.api.web.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(CreateUserRequest createUserRequest);

    UserResponse getById(Long id);

    List<UserResponse> getAll();

    UserResponse update(UpdateUserRequest updateUserRequest);

    void delete(Long id);
}
