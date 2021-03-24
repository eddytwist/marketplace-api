package com.edik.car.api.web.mapper;

import com.edik.car.api.dao.model.UserInformation;
import com.edik.car.api.web.dto.request.UserInformationRequest;
import com.edik.car.api.web.dto.response.UserInformationResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserInformationMapper {

    public static UserInformationResponse toUserInformationResponse(UserInformation userInformation) {
        if (userInformation == null) {
            return null;
        }

        return UserInformationResponse.builder()
            .name(userInformation.getName())
            .build();
    }

    public static UserInformation toUserInformation(UserInformationRequest userInformationRequest) {
        if (userInformationRequest == null) {
            return null;
        }

        return UserInformation.builder()
            .name(userInformationRequest.getName())
            .build();
    }

    public static UserInformation toUserInformation(String userInformationRequest) {
        if (userInformationRequest == null) {
            return null;
        }

        return UserInformation.builder()
            .name(userInformationRequest)
            .build();
    }
}
