package com.edik.car.api.web.mapper;

import com.edik.car.api.dao.model.User;
import com.edik.car.api.web.dto.request.CreateUserRequest;
import com.edik.car.api.web.dto.request.UpdateUserRequest;
import com.edik.car.api.web.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }

    public static User toUser(CreateUserRequest request) {
        if (request == null) {
            return null;
        }

        User user = User.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .email(request.getEmail())
            .build();

        setUserInformation(user, request);
        setUserPhones(user, request);

        return user;
    }

    public static void setUserInformation(User user, CreateUserRequest request) {
        if (request.getName() == null) {
            return;
        }

        user.addUserInformation(UserInformationMapper.toUserInformation(request.getName()));
    }

    private static void setUserInformation(User user, UpdateUserRequest request) {
        if (request.getName() == null) {
            return;
        }

        user.addUserInformation(UserInformationMapper.toUserInformation(request.getName()));
    }

    private static void setUserPhones(User user, CreateUserRequest request) {
        if (request.getUserPhones() == null) {
            return;
        }

        request.getUserPhones().stream()
            .map(UserPhoneMapper::toUserPhone)
            .forEach(user::addUserPhone);
    }

    private static void setUserPhones(User user, UpdateUserRequest request) {
        if (request.getUserPhones() == null) {
            return;
        }

        user.getUserPhones().clear();

        request.getUserPhones().stream()
            .map(UserPhoneMapper::toUserPhone)
            .forEach(user::addUserPhone);
    }

    public static void updateUserFields(User userToUpdate, UpdateUserRequest userFromRequest) {
        userToUpdate.setUsername(userFromRequest.getUsername());
        userToUpdate.setEmail(userFromRequest.getEmail());
        userToUpdate.setPassword(userFromRequest.getPassword());
        setUserPhones(userToUpdate, userFromRequest);
        setUserInformation(userToUpdate, userFromRequest);
    }
}
