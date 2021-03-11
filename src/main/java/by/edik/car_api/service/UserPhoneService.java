package by.edik.car_api.service;

import by.edik.car_api.web.dto.request.CreateUserPhoneRequest;
import by.edik.car_api.web.dto.request.UpdateUserPhoneRequest;
import by.edik.car_api.web.dto.response.UserPhoneResponse;

import java.util.List;

public interface UserPhoneService {

    UserPhoneResponse create(CreateUserPhoneRequest createUserPhoneRequest);

    UserPhoneResponse getById(Long id);

    List<UserPhoneResponse> getAll();

    void update(UpdateUserPhoneRequest updateUserPhoneRequest);

    void delete(Long id);
}
