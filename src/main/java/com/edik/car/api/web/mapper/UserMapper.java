package com.edik.car.api.web.mapper;

import com.edik.car.api.dao.model.User;
import com.edik.car.api.dao.model.UserPhone;
import com.edik.car.api.web.dto.request.CreateUserRequest;
import com.edik.car.api.web.dto.request.UpdateUserRequest;
import com.edik.car.api.web.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    private static void setUserInformation(User user, CreateUserRequest request) {
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


    public static User toUser(UpdateUserRequest updateUserRequest) {
        if (updateUserRequest == null) {
            return null;
        }

        return User.builder()
            .userId(updateUserRequest.getUserId())
            .username(updateUserRequest.getUsername())
            .password(updateUserRequest.getPassword())
            .email(updateUserRequest.getEmail())
            .build();
    }

    private static List<UserPhone> getUserPhones(CreateUserRequest request) {
        if (request.getUserPhones() == null) {
            return Collections.emptyList();
        }

        return request.getUserPhones()
            .stream()
            .map(UserPhoneMapper::toUserPhone)
            .collect(Collectors.toList());
    }
}
