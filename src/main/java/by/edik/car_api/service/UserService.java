package by.edik.car_api.service;

import by.edik.car_api.model.User;
import by.edik.car_api.web.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(User user);

    UserDto getById(Long id);

    List<UserDto> getAll();

    void update(User user);

    void delete(Long id);
}
