package com.edik.car.api.service.impl;

import com.edik.car.api.dao.PictureDao;
import com.edik.car.api.dao.model.Picture;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.PictureService;
import com.edik.car.api.service.exception.ServiceFailedException;
import com.edik.car.api.web.dto.request.CreatePictureRequest;
import com.edik.car.api.web.dto.request.UpdatePictureRequest;
import com.edik.car.api.web.dto.response.PictureResponse;
import com.edik.car.api.web.mapper.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "pictureService")
public final class PictureServiceImpl extends AbstractService implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public PictureResponse create(CreatePictureRequest createPictureRequest) {
        Picture pictureToCreate = PictureMapper.toPicture(createPictureRequest);
        Picture createdPicture;

        try {
            begin();

            createdPicture = pictureDao.save(pictureToCreate);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + createPictureRequest, e);
        }

        return PictureMapper.toPictureResponse(createdPicture);
    }

    @Override
    public PictureResponse getById(Long id) {
        Picture picture;

        try {
            begin();

            picture = pictureDao.findById(id);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Picture with id: " + id, e);
        }

        return PictureMapper.toPictureResponse(picture);
    }

    @Override
    public List<PictureResponse> getAll() {
        List<Picture> pictures;

        try {
            begin();

            pictures = pictureDao.findAll();

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Pictures.", e);
        }

        return pictures
            .stream()
            .map(PictureMapper::toPictureResponse)
            .collect(Collectors.toList());
    }

    @Override
    public void update(UpdatePictureRequest updatePictureRequest) {
        Picture picture = PictureMapper.toPicture(updatePictureRequest);

        try {
            begin();

            pictureDao.update(picture);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't update Picture: " + updatePictureRequest, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            begin();

            pictureDao.delete(id);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't delete Picture id: " + id, e);
        }
    }
}
