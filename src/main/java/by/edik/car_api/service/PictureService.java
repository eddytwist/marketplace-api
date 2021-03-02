package by.edik.car_api.service;

import by.edik.car_api.dao.model.Picture;
import by.edik.car_api.web.dto.PictureCreatedDto;
import by.edik.car_api.web.dto.PictureDto;

import java.util.List;

public interface PictureService {
    PictureDto create(PictureCreatedDto pictureCreatedDto);

    PictureDto getById(Long id);

    List<PictureDto> getAll();

    void update(Picture Picture);

    void delete(Long id);
}
