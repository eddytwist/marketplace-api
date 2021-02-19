package by.edik.car_api.service;

import by.edik.car_api.model.UserInformation;
import by.edik.car_api.web.dto.UserInformationDto;

import java.util.List;

public interface UserInformationService {
    UserInformationDto create(UserInformation userInformation);

    UserInformationDto getById(long id);

    List<UserInformationDto> getAll();

    void update(UserInformation userInformation);

    void delete(long id);
}
