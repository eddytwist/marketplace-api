package com.edik.car.api.service.impl;

import com.edik.car.api.dao.model.UserPhone;
import com.edik.car.api.repository.UserPhoneRepository;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.UserPhoneService;
import com.edik.car.api.service.exception.ServiceEntityNotFoundException;
import com.edik.car.api.web.dto.request.CreateUserPhoneRequest;
import com.edik.car.api.web.dto.request.UpdateUserPhoneRequest;
import com.edik.car.api.web.dto.response.UserPhoneResponse;
import com.edik.car.api.web.mapper.UserPhoneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userPhoneService")
@RequiredArgsConstructor
public class UserPhoneServiceImpl extends AbstractService implements UserPhoneService {

    private final UserPhoneRepository userPhoneRepository;

    @Override
    @Transactional
    public UserPhoneResponse create(CreateUserPhoneRequest createUserPhoneRequest) {
        UserPhone userPhoneToCreate = UserPhoneMapper.toUserPhone(createUserPhoneRequest);

        UserPhone createdUserPhone = userPhoneRepository.save(userPhoneToCreate);

        return UserPhoneMapper.toUserPhoneResponse(createdUserPhone);
    }

    @Override
    @Transactional
    public UserPhoneResponse getById(Long id) {
        UserPhone userPhone = userPhoneRepository.findById(id)
            .orElseThrow(() -> new ServiceEntityNotFoundException("UserPhone", id));
        return UserPhoneMapper.toUserPhoneResponse(userPhone);
    }

    @Override
    @Transactional
    public List<UserPhoneResponse> getAll() {
        return userPhoneRepository.findAll()
            .stream()
            .map(UserPhoneMapper::toUserPhoneResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(UpdateUserPhoneRequest updateUserPhoneRequest) {
        UserPhone userPhone = UserPhoneMapper.toUserPhone(updateUserPhoneRequest);
        userPhoneRepository.save(userPhone);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userPhoneRepository.deleteById(id);
    }
}
