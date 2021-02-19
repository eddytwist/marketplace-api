package by.edik.car_api.service;

import by.edik.car_api.model.Picture;
import by.edik.car_api.web.dto.PictureDto;

import java.util.List;

public interface PictureService {
    PictureDto create(Picture Picture);

    PictureDto getById(long id);

    List<PictureDto> getAll();

    void update(Picture Picture);

    void delete(long id);
}
