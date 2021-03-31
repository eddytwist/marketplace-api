package com.edik.car.api.service.impl;

import com.edik.car.api.dao.model.UserInformation;
import com.edik.car.api.repository.UserInformationRepository;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.UserInformationService;
import com.edik.car.api.service.exception.ServiceEntityNotFoundException;
import com.edik.car.api.web.dto.request.UserInformationRequest;
import com.edik.car.api.web.dto.response.UserInformationResponse;
import com.edik.car.api.web.mapper.UserInformationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userInformationService")
@RequiredArgsConstructor
public class UserInformationServiceImpl extends AbstractService implements UserInformationService {

    private final UserInformationRepository userInformationRepository;

    @Override
    @Transactional
    public UserInformationResponse create(UserInformationRequest userInformationRequest) {
        UserInformation userInformationToCreate = UserInformationMapper.toUserInformation(userInformationRequest);

        UserInformation createdUserInformation = userInformationRepository.save(userInformationToCreate);

        return UserInformationMapper.toUserInformationResponse(createdUserInformation);
    }

    @Override
    @Transactional
    public UserInformationResponse getById(Long id) {
        UserInformation userInformation = userInformationRepository.findById(id)
            .orElseThrow(() -> new ServiceEntityNotFoundException("UserInformation", id));
        return UserInformationMapper.toUserInformationResponse(userInformation);
    }

    @Override
    @Transactional
    public List<UserInformationResponse> getAll() {
        return userInformationRepository.findAll()
            .stream()
            .map(UserInformationMapper::toUserInformationResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(UserInformationRequest userInformationRequest) {
        UserInformation userInformation = UserInformationMapper.toUserInformation(userInformationRequest);

        userInformationRepository.save(userInformation);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userInformationRepository.deleteById(id);
    }
}
