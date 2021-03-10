package by.edik.car_api.web.mapper;

import by.edik.car_api.dao.model.User;
import by.edik.car_api.web.dto.request.CreateUserRequest;
import by.edik.car_api.web.dto.request.UpdateUserRequest;
import by.edik.car_api.web.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }

    public static User createUserRequestToUser(CreateUserRequest createUserRequest) {
        return User.builder()
            .username(createUserRequest.getUsername())
            .password(createUserRequest.getPassword())
            .email(createUserRequest.getEmail())
            .build();
    }

    public static User updateUserRequestToUser(UpdateUserRequest updateUserRequest) {
        return User.builder()
            .userId(updateUserRequest.getUserId())
            .username(updateUserRequest.getUsername())
            .password(updateUserRequest.getPassword())
            .email(updateUserRequest.getEmail())
            .build();
    }
}
