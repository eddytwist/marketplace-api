package by.edik.car_api.service.impl;

import by.edik.car_api.dao.UserPhoneDao;
import by.edik.car_api.dao.model.UserPhone;
import by.edik.car_api.service.UserPhoneService;
import by.edik.car_api.web.dto.UserPhoneDto;
import by.edik.car_api.web.mapper.UserPhoneMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserPhoneServiceImpl implements UserPhoneService {

    private static volatile UserPhoneServiceImpl userPhoneServiceImplInstance;

    private final UserPhoneDao userPhoneDao = UserPhoneDao.getInstance();

    @Override
    public UserPhoneDto create(UserPhone userPhone) {
        return UserPhoneMapper.userPhoneToUserPhoneDto(userPhoneDao.create(userPhone));
    }

    @Override
    public UserPhoneDto getById(Long id) {
        return UserPhoneMapper.userPhoneToUserPhoneDto(userPhoneDao.getById(id));
    }

    @Override
    public List<UserPhoneDto> getAll() {
        return userPhoneDao.getAll().stream()
            .map(UserPhoneMapper::userPhoneToUserPhoneDto)
            .collect(Collectors.toList());
    }

    @Override
    public void update(UserPhone userPhone) {
        userPhoneDao.update(userPhone);
    }

    @Override
    public void delete(Long id) {
        userPhoneDao.delete(id);
    }

    public static UserPhoneServiceImpl getInstance() {
        UserPhoneServiceImpl localInstance = userPhoneServiceImplInstance;

        if (localInstance == null) {

            synchronized (UserPhoneServiceImpl.class) {
                localInstance = userPhoneServiceImplInstance;

                if (localInstance == null) {
                    userPhoneServiceImplInstance = localInstance = new UserPhoneServiceImpl();
                }
            }
        }

        return localInstance;
    }
}
