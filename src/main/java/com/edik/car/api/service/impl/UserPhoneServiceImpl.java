package com.edik.car.api.service.impl;

import com.edik.car.api.dao.UserPhoneDao;
import com.edik.car.api.dao.model.UserPhone;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.UserPhoneService;
import com.edik.car.api.service.exception.ServiceFailedException;
import com.edik.car.api.web.dto.request.CreateUserPhoneRequest;
import com.edik.car.api.web.dto.request.UpdateUserPhoneRequest;
import com.edik.car.api.web.dto.response.UserPhoneResponse;
import com.edik.car.api.web.mapper.UserPhoneMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
            begin();

            createdUserPhone = userPhoneDao.save(userPhoneToCreate);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + createUserPhoneRequest, e);
        }

        return UserPhoneMapper.toUserPhoneResponse(createdUserPhone);
    }

    @Override
    public UserPhoneResponse getById(Long id) {
        UserPhone userPhone;

        try {
            begin();

            userPhone = userPhoneDao.findById(id);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find UserPhone with id: " + id, e);
        }

        return UserPhoneMapper.toUserPhoneResponse(userPhone);
    }

    @Override
    public List<UserPhoneResponse> getAll() {
        List<UserPhone> userPhones;

        try {
            begin();

            userPhones = userPhoneDao.findAll();

            commit();
        } catch (Exception e) {
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
            begin();

            userPhoneDao.update(userPhone);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't update UserPhone: " + updateUserPhoneRequest, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            begin();

            userPhoneDao.delete(id);

            commit();
        } catch (Exception e) {
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
