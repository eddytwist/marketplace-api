package by.edik.car_api.web.mapper;

import by.edik.car_api.model.User;
import by.edik.car_api.web.dto.UserDto;

public class UserMapper {

    public static UserDto userToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}


