package by.edik.car_api.service.impl;

import by.edik.car_api.dao.UserDao;
import by.edik.car_api.dao.model.User;
import by.edik.car_api.service.UserService;
import by.edik.car_api.web.dto.UserCreatedDto;
import by.edik.car_api.web.dto.UserDto;
import by.edik.car_api.web.dto.UserUpdatedDto;
import by.edik.car_api.web.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static volatile UserServiceImpl userServiceInstance;

    private final UserDao userDao = UserDao.getInstance();

    @Override
    public UserDto create(UserCreatedDto userCreatedDto) {
        User user = UserMapper.createdUserDtoToUser(userCreatedDto);
        return UserMapper.userToUserDto(userDao.create(user));
    }

    @Override
    public UserDto getById(Long id) {
        return UserMapper.userToUserDto(userDao.getById(id));
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream()
            .map(UserMapper::userToUserDto)
            .collect(Collectors.toList());
    }

    public UserDto update(UserUpdatedDto userUpdatedDto) {
        userDao.update(UserMapper.updatedUserDtoToUser(userUpdatedDto));
        return getById(userUpdatedDto.getUserId());
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    public static UserServiceImpl getInstance() {
        UserServiceImpl localInstance = userServiceInstance;
        if (localInstance == null) {
            synchronized (UserServiceImpl.class) {
                localInstance = userServiceInstance;
                if (localInstance == null) {
                    userServiceInstance = localInstance = new UserServiceImpl();
                }
            }
        }
        return localInstance;
    }
}
