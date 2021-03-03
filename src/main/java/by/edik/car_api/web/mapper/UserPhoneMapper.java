package by.edik.car_api.web.mapper;

import by.edik.car_api.dao.model.UserPhone;
import by.edik.car_api.web.dto.UserPhoneDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserPhoneMapper {

    public static UserPhoneDto userPhoneToUserPhoneDto(UserPhone userPhone) {
        return UserPhoneDto.builder()
            .phoneNumberId(userPhone.getPhoneNumberId())
            .phoneNumber(userPhone.getPhoneNumber())
            .build();
    }
}


