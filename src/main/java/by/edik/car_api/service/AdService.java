package by.edik.car_api.service;

import by.edik.car_api.web.dto.AdCreatedDto;
import by.edik.car_api.web.dto.AdDto;
import by.edik.car_api.web.dto.AdUpdatedDto;

import java.util.List;

public interface AdService {
    AdDto create(AdCreatedDto adCreatedDto);

    AdDto getById(Long id);

    List<AdDto> getAll();

    AdDto update(AdUpdatedDto adUpdatedDto);

    void delete(Long id);
}
