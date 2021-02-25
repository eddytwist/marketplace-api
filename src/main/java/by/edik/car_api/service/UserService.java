package by.edik.car_api.service;

import by.edik.car_api.model.User;
import by.edik.car_api.web.dto.CreatedUserDto;
import by.edik.car_api.web.dto.UpdatedUserDto;
import by.edik.car_api.web.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(CreatedUserDto createdUserDto);

    UserDto getById(Long id);

    List<UserDto> getAll();

    UserDto update(UpdatedUserDto updatedUserDto);

    void delete(Long id);
}
