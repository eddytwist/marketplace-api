package com.edik.car.api.service.impl;

import com.edik.car.api.dao.model.Picture;
import com.edik.car.api.repository.PictureRepository;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.PictureService;
import com.edik.car.api.service.exception.ServiceEntityNotFoundException;
import com.edik.car.api.web.dto.request.CreatePictureRequest;
import com.edik.car.api.web.dto.request.UpdatePictureRequest;
import com.edik.car.api.web.dto.response.PictureResponse;
import com.edik.car.api.web.mapper.PictureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("pictureService")
@RequiredArgsConstructor
public class PictureServiceImpl extends AbstractService implements PictureService {

    private final PictureRepository pictureRepository;

    @Override
    @Transactional
    public PictureResponse create(CreatePictureRequest createPictureRequest) {
        Picture pictureToCreate = PictureMapper.toPicture(createPictureRequest);

        Picture createdPicture = pictureRepository.save(pictureToCreate);

        return PictureMapper.toPictureResponse(createdPicture);
    }

    @Override
    @Transactional
    public PictureResponse getById(Long id) {

        Picture picture = pictureRepository.findById(id)
            .orElseThrow(() -> new ServiceEntityNotFoundException("Picture", id));

        return PictureMapper.toPictureResponse(picture);
    }

    @Override
    @Transactional
    public List<PictureResponse> getAll() {
        return pictureRepository.findAll()
            .stream()
            .map(PictureMapper::toPictureResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(UpdatePictureRequest updatePictureRequest) {
        Picture picture = PictureMapper.toPicture(updatePictureRequest);

        pictureRepository.save(picture);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pictureRepository.deleteById(id);
    }
}
