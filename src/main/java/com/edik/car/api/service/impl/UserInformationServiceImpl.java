package com.edik.car.api.service.impl;

import com.edik.car.api.dao.UserInformationDao;
import com.edik.car.api.dao.model.UserInformation;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.UserInformationService;
import com.edik.car.api.service.exception.ServiceFailedException;
import com.edik.car.api.web.dto.request.UserInformationRequest;
import com.edik.car.api.web.dto.response.UserInformationResponse;
import com.edik.car.api.web.mapper.UserInformationMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserInformationServiceImpl extends AbstractService implements UserInformationService {

    private static volatile UserInformationServiceImpl userInformationServiceImplInstance;

    private final UserInformationDao userInformationDao = UserInformationDao.getInstance();

    @Override
    public UserInformationResponse create(UserInformationRequest userInformationRequest) {
        UserInformation userInformationToCreate = UserInformationMapper.toUserInformation(userInformationRequest);
        UserInformation createdUserInformation;

        try {
            begin();
            createdUserInformation = userInformationDao.save(userInformationToCreate);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + userInformationRequest, e);
        }

        return UserInformationMapper.toUserInformationResponse(createdUserInformation);
    }

    @Override
    public UserInformationResponse getById(Long id) {
        UserInformation userInformation;

        try {
            begin();
            userInformation = userInformationDao.findById(id);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Picture with id: " + id, e);
        }

        return UserInformationMapper.toUserInformationResponse(userInformation);
    }

    @Override
    public List<UserInformationResponse> getAll() {
        List<UserInformation> usersInformation;

        try {
            begin();
            usersInformation = userInformationDao.findAll();
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find UsersInformation.", e);
        }

        return usersInformation.stream()
            .map(UserInformationMapper::toUserInformationResponse)
            .collect(Collectors.toList());
    }

    @Override
    public void update(UserInformationRequest userInformationRequest) {
        UserInformation userInformation = UserInformationMapper.toUserInformation(userInformationRequest);

        try {
            begin();
            userInformationDao.update(userInformation);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't update UserInformation: " + userInformationRequest, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            begin();
            userInformationDao.delete(id);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't delete UserInformation id: " + id, e);
        }
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
