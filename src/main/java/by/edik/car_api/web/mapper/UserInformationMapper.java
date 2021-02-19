package by.edik.car_api.web.mapper;

import by.edik.car_api.model.UserInformation;
import by.edik.car_api.web.dto.UserInformationDto;

public class UserInformationMapper {

    public static UserInformationDto userInformationToUserInformationDto(UserInformation userInformation) {
        return UserInformationDto.builder()
                .name(userInformation.getName())
                .build();
    }
}


