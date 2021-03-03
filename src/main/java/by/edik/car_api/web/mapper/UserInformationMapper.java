package by.edik.car_api.web.mapper;

import by.edik.car_api.dao.model.UserInformation;
import by.edik.car_api.web.dto.UserInformationDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserInformationMapper {

    public static UserInformationDto userInformationToUserInformationDto(UserInformation userInformation) {
        return UserInformationDto.builder()
            .name(userInformation.getName())
            .build();
    }
}


