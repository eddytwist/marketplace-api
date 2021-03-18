package com.edik.car.api.service;

import com.edik.car.api.web.dto.request.UserInformationRequest;
import com.edik.car.api.web.dto.response.UserInformationResponse;

import java.util.List;

public interface UserInformationService {

    UserInformationResponse create(UserInformationRequest userInformationRequest);

    UserInformationResponse getById(Long id);

    List<UserInformationResponse> getAll();

    void update(UserInformationRequest userInformationRequest);

    void delete(Long id);
}
