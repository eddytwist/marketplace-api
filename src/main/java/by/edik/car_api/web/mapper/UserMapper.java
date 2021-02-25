package by.edik.car_api.web.mapper;

import by.edik.car_api.model.User;
import by.edik.car_api.web.dto.CreatedUserDto;
import by.edik.car_api.web.dto.UpdatedUserDto;
import by.edik.car_api.web.dto.UserDto;

public class UserMapper {

    public static UserDto userToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static User createdUserDtoToUser(CreatedUserDto createdUserDto) {
        return User.builder()
                .username(createdUserDto.getUsername())
                .password(createdUserDto.getPassword())
                .email(createdUserDto.getEmail())
                .build();
    }

    public static User updatedUserDtoToUser(UpdatedUserDto updatedUserDto) {
        return User.builder()
                .userId(updatedUserDto.getUserId())
                .username(updatedUserDto.getUsername())
                .password(updatedUserDto.getPassword())
                .email(updatedUserDto.getEmail())
                .build();
    }
}


