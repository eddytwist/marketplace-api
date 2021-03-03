package by.edik.car_api.service.impl;

import by.edik.car_api.dao.PictureDao;
import by.edik.car_api.dao.model.Picture;
import by.edik.car_api.service.PictureService;
import by.edik.car_api.web.dto.PictureCreatedDto;
import by.edik.car_api.web.dto.PictureDto;
import by.edik.car_api.web.mapper.PictureMapper;

import java.util.List;
import java.util.stream.Collectors;

public class PictureServiceImpl implements PictureService {

    private static volatile PictureServiceImpl pictureServiceImplInstance;

    private final PictureDao pictureDao = PictureDao.getInstance();

    @Override
    public PictureDto create(PictureCreatedDto pictureCreatedDto) {
        Picture picture = PictureMapper.pictureCreatedDtoToPicture(pictureCreatedDto);

        return PictureMapper.pictureToPictureDto(pictureDao.create(picture));
    }

    @Override
    public PictureDto getById(Long id) {
        return PictureMapper.pictureToPictureDto(pictureDao.getById(id));
    }

    @Override
    public List<PictureDto> getAll() {
        return pictureDao.getAll().stream()
            .map(PictureMapper::pictureToPictureDto)
            .collect(Collectors.toList());
    }

    @Override
    public void update(Picture picture) {
        pictureDao.update(picture);
    }

    @Override
    public void delete(Long id) {
        pictureDao.delete(id);
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
