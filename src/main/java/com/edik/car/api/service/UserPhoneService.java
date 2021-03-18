package com.edik.car.api.service;

import com.edik.car.api.web.dto.request.CreateUserPhoneRequest;
import com.edik.car.api.web.dto.request.UpdateUserPhoneRequest;
import com.edik.car.api.web.dto.response.UserPhoneResponse;

import java.util.List;

public interface UserPhoneService {

    UserPhoneResponse create(CreateUserPhoneRequest createUserPhoneRequest);

    UserPhoneResponse getById(Long id);

    List<UserPhoneResponse> getAll();

    void update(UpdateUserPhoneRequest updateUserPhoneRequest);

    void delete(Long id);
}
