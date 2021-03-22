package com.edik.car.api.web.mapper;

import com.edik.car.api.dao.model.UserPhone;
import com.edik.car.api.web.dto.request.CreateUserPhoneRequest;
import com.edik.car.api.web.dto.request.UpdateUserPhoneRequest;
import com.edik.car.api.web.dto.response.UserPhoneResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserPhoneMapper {

    public static UserPhoneResponse toUserPhoneResponse(UserPhone userPhone) {
        if (userPhone == null) {
            return null;
        }

        return UserPhoneResponse.builder()
            .phoneNumberId(userPhone.getPhoneNumberId())
            .phoneNumber(userPhone.getPhoneNumber())
            .build();
    }

    public static UserPhone toUserPhone(CreateUserPhoneRequest createUserPhoneRequest) {
        if (createUserPhoneRequest == null) {
            return null;
        }

        return UserPhone.builder()
//            .userId(createUserPhoneRequest.getUserId())
            .phoneNumber(createUserPhoneRequest.getPhoneNumber())
            .build();
    }

    public static UserPhone toUserPhone(String userPhoneRequest) {
        if (userPhoneRequest == null) {
            return null;
        }

        return UserPhone.builder()
            .phoneNumber(userPhoneRequest)
            .build();
    }

    public static UserPhone toUserPhone(UpdateUserPhoneRequest updateUserPhoneRequest) {
        if (updateUserPhoneRequest == null) {
            return null;
        }

        return UserPhone.builder()
//            .userId(updateUserPhoneRequest.getUserId())
            .phoneNumberId(updateUserPhoneRequest.getPhoneNumberId())
            .phoneNumber(updateUserPhoneRequest.getPhoneNumber())
            .build();
    }
}
