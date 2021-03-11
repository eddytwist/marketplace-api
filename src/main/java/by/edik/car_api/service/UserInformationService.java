package by.edik.car_api.service;

import by.edik.car_api.web.dto.request.UserInformationRequest;
import by.edik.car_api.web.dto.response.UserInformationResponse;

import java.util.List;

public interface UserInformationService {

    UserInformationResponse create(UserInformationRequest userInformationRequest);

    UserInformationResponse getById(Long id);

    List<UserInformationResponse> getAll();

    void update(UserInformationRequest userInformationRequest);

    void delete(Long id);
}
