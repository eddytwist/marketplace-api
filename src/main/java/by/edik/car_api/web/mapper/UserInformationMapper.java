package by.edik.car_api.web.mapper;

import by.edik.car_api.dao.model.UserInformation;
import by.edik.car_api.web.dto.request.UserInformationRequest;
import by.edik.car_api.web.dto.response.UserInformationResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserInformationMapper {

    public static UserInformationResponse userInformationToUserInformationResponse(UserInformation userInformation) {
        return UserInformationResponse.builder()
            .name(userInformation.getName())
            .build();
    }

    public static UserInformation userInformationRequestToUserInformation(UserInformationRequest userInformationRequest) {
        return UserInformation.builder()
            .userId(userInformationRequest.getUserId())
            .name(userInformationRequest.getName())
            .build();
    }
}
