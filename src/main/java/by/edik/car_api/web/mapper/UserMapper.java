package by.edik.car_api.web.mapper;

import by.edik.car_api.dao.model.User;
import by.edik.car_api.web.dto.UserCreatedDto;
import by.edik.car_api.web.dto.UserDto;
import by.edik.car_api.web.dto.UserUpdatedDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static UserDto userToUserDto(User user) {
        return UserDto.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }

    public static User createdUserDtoToUser(UserCreatedDto userCreatedDto) {
        return User.builder()
            .username(userCreatedDto.getUsername())
            .password(userCreatedDto.getPassword())
            .email(userCreatedDto.getEmail())
            .build();
    }

    public static User updatedUserDtoToUser(UserUpdatedDto userUpdatedDto) {
        return User.builder()
            .userId(userUpdatedDto.getUserId())
            .username(userUpdatedDto.getUsername())
            .password(userUpdatedDto.getPassword())
            .email(userUpdatedDto.getEmail())
            .build();
    }
}


