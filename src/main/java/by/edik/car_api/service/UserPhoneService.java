package by.edik.car_api.service;

import by.edik.car_api.dao.model.UserPhone;
import by.edik.car_api.web.dto.UserPhoneDto;

import java.util.List;

public interface UserPhoneService {

    UserPhoneDto create(UserPhone userPhone);

    UserPhoneDto getById(Long id);

    List<UserPhoneDto> getAll();

    void update(UserPhone userPhone);

    void delete(Long id);
}
