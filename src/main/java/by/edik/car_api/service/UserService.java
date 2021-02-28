package by.edik.car_api.service;

import by.edik.car_api.web.dto.UserCreatedDto;
import by.edik.car_api.web.dto.UserDto;
import by.edik.car_api.web.dto.UserUpdatedDto;

import java.util.List;

public interface UserService {
    UserDto create(UserCreatedDto userCreatedDto);

    UserDto getById(Long id);

    List<UserDto> getAll();

    UserDto update(UserUpdatedDto userUpdatedDto);

    void delete(Long id);
}
