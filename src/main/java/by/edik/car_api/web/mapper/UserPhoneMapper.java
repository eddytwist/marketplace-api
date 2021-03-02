package by.edik.car_api.web.mapper;

import by.edik.car_api.dao.model.UserPhone;
import by.edik.car_api.web.dto.UserPhoneDto;

public class UserPhoneMapper {

    public static UserPhoneDto userPhoneToUserPhoneDto(UserPhone userPhone) {
        return UserPhoneDto.builder()
            .phoneNumberId(userPhone.getPhoneNumberId())
            .phoneNumber(userPhone.getPhoneNumber())
            .build();
    }
}


