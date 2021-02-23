package by.edik.car_api.service;

import by.edik.car_api.model.Ad;
import by.edik.car_api.web.dto.AdDto;

import java.util.List;

public interface AdService {
    AdDto create(Ad ad);

    AdDto getById(Long id);

    List<AdDto> getAll();

    void update(Ad ad);

    void delete(Long id);
}
