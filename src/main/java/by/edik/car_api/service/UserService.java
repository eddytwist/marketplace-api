package by.edik.car_api.service;

import by.edik.car_api.web.dto.request.CreateUserRequest;
import by.edik.car_api.web.dto.request.UpdateUserRequest;
import by.edik.car_api.web.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(CreateUserRequest createUserRequest);

    UserResponse getById(Long id);

    List<UserResponse> getAll();

    UserResponse update(UpdateUserRequest updateUserRequest);

    void delete(Long id);
}
