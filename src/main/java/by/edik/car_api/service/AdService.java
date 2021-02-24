package by.edik.car_api.service;

import by.edik.car_api.model.Ad;
import by.edik.car_api.web.dto.AdDto;
import by.edik.car_api.web.dto.CreatedAdDto;

import java.util.List;

public interface AdService {
    AdDto create(CreatedAdDto createdAdDto);

    AdDto getById(Long id);

    List<AdDto> getAll();

    void update(Ad ad);

    void delete(Long id);
}
