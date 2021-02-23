package by.edik.car_api.service.impl;

import by.edik.car_api.dao.UserInformationDao;
import by.edik.car_api.model.UserInformation;
import by.edik.car_api.service.UserInformationService;
import by.edik.car_api.web.dto.UserInformationDto;
import by.edik.car_api.web.mapper.UserInformationMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserInformationServiceImpl implements UserInformationService {

    private static volatile UserInformationServiceImpl userInformationServiceImplInstance;

    private final UserInformationDao userInformationDao = UserInformationDao.getInstance();

    @Override
    public UserInformationDto create(UserInformation userInformation) {
        return UserInformationMapper.userInformationToUserInformationDto(userInformationDao.create(userInformation));
    }

    @Override
    public UserInformationDto getById(Long id) {
        return UserInformationMapper.userInformationToUserInformationDto(userInformationDao.getById(id));
    }

    @Override
    public List<UserInformationDto> getAll() {
        return userInformationDao.getAll().stream()
                .map(UserInformationMapper::userInformationToUserInformationDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(UserInformation userInformation) {
        userInformationDao.update(userInformation);
    }

    @Override
    public void delete(Long id) {
        userInformationDao.delete(id);
    }

    public static UserInformationServiceImpl getInstance() {
        UserInformationServiceImpl localInstance = userInformationServiceImplInstance;
        if (localInstance == null) {
            synchronized (UserInformationServiceImpl.class) {
                localInstance = userInformationServiceImplInstance;
                if (localInstance == null) {
                    userInformationServiceImplInstance = localInstance = new UserInformationServiceImpl();
                }
            }
        }
        return localInstance;
    }
}
