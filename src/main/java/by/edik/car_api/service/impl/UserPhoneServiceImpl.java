package by.edik.car_api.service.impl;

import by.edik.car_api.dao.UserPhoneDao;
import by.edik.car_api.dao.model.UserPhone;
import by.edik.car_api.service.AbstractService;
import by.edik.car_api.service.UserPhoneService;
import by.edik.car_api.service.exception.ServiceFailedException;
import by.edik.car_api.web.dto.request.CreateUserPhoneRequest;
import by.edik.car_api.web.dto.request.UpdateUserPhoneRequest;
import by.edik.car_api.web.dto.response.UserPhoneResponse;
import by.edik.car_api.web.mapper.UserPhoneMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserPhoneServiceImpl extends AbstractService implements UserPhoneService {

    private static volatile UserPhoneServiceImpl userPhoneServiceImplInstance;

    private final UserPhoneDao userPhoneDao = UserPhoneDao.getInstance();

    @Override
    public UserPhoneResponse create(CreateUserPhoneRequest createUserPhoneRequest) {
        UserPhone userPhoneToCreate = UserPhoneMapper.toUserPhone(createUserPhoneRequest);
        UserPhone createdUserPhone;

        try {
            startTransaction();
            createdUserPhone = userPhoneDao.save(userPhoneToCreate);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + createUserPhoneRequest, e);
        }

        return UserPhoneMapper.toUserPhoneResponse(createdUserPhone);
    }

    @Override
    public UserPhoneResponse getById(Long id) {
        UserPhone userPhone;

        try {
            startTransaction();
            userPhone = userPhoneDao.findById(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find UserPhone with id: " + id, e);
        }

        return UserPhoneMapper.toUserPhoneResponse(userPhone);
    }

    @Override
    public List<UserPhoneResponse> getAll() {
        List<UserPhone> userPhones;

        try {
            startTransaction();
            userPhones = userPhoneDao.findAll();
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find UserPhones.", e);
        }

        return userPhones.stream()
            .map(UserPhoneMapper::toUserPhoneResponse)
            .collect(Collectors.toList());
    }

    @Override
    public void update(UpdateUserPhoneRequest updateUserPhoneRequest) {
        UserPhone userPhone = UserPhoneMapper.toUserPhone(updateUserPhoneRequest);

        try {
            startTransaction();
            userPhoneDao.update(userPhone);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't update UserPhone: " + updateUserPhoneRequest, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            startTransaction();
            userPhoneDao.delete(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't delete UserPhone id: " + id, e);
        }
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
