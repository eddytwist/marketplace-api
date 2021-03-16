package by.edik.car_api.service.impl;

import by.edik.car_api.dao.PictureDao;
import by.edik.car_api.dao.model.Picture;
import by.edik.car_api.service.AbstractService;
import by.edik.car_api.service.PictureService;
import by.edik.car_api.service.exception.ServiceFailedException;
import by.edik.car_api.web.dto.request.CreatePictureRequest;
import by.edik.car_api.web.dto.request.UpdatePictureRequest;
import by.edik.car_api.web.dto.response.PictureResponse;
import by.edik.car_api.web.mapper.PictureMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PictureServiceImpl extends AbstractService implements PictureService {

    private static volatile PictureServiceImpl pictureServiceImplInstance;

    private final PictureDao pictureDao = PictureDao.getInstance();

    @Override
    public PictureResponse create(CreatePictureRequest createPictureRequest) {
        Picture pictureToCreate = PictureMapper.toPicture(createPictureRequest);
        Picture createdPicture;

        try {
            startTransaction();
            createdPicture = pictureDao.save(pictureToCreate);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + createPictureRequest, e);
        }

        return PictureMapper.toPictureResponse(createdPicture);
    }

    @Override
    public PictureResponse getById(Long id) {
        Picture picture;

        try {
            startTransaction();
            picture = pictureDao.findById(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Picture with id: " + id, e);
        }

        return PictureMapper.toPictureResponse(picture);
    }

    @Override
    public List<PictureResponse> getAll() {
        List<Picture> pictures;

        try {
            startTransaction();
            pictures = pictureDao.findAll();
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Pictures.", e);
        }

        return pictures.stream()
            .map(PictureMapper::toPictureResponse)
            .collect(Collectors.toList());
    }

    @Override
    public void update(UpdatePictureRequest updatePictureRequest) {
        Picture picture = PictureMapper.toPicture(updatePictureRequest);

        try {
            startTransaction();
            pictureDao.update(picture);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't update Picture: " + updatePictureRequest, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            startTransaction();
            pictureDao.delete(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't delete Picture id: " + id, e);
        }
    }

    public static PictureServiceImpl getInstance() {
        PictureServiceImpl localInstance = pictureServiceImplInstance;

        if (localInstance == null) {

            synchronized (PictureServiceImpl.class) {
                localInstance = pictureServiceImplInstance;

                if (localInstance == null) {
                    pictureServiceImplInstance = localInstance = new PictureServiceImpl();
                }
            }
        }

        return localInstance;
    }
}
