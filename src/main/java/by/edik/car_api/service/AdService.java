package by.edik.car_api.service;

import by.edik.car_api.web.dto.AdCreatedDto;
import by.edik.car_api.web.dto.AdDto;
import by.edik.car_api.web.dto.AdFullInformationDto;
import by.edik.car_api.web.dto.AdPatchedDto;
import by.edik.car_api.web.dto.AdShortInformationDto;
import by.edik.car_api.web.dto.AdUpdatedDto;

import java.util.List;

public interface AdService {

    AdDto create(AdCreatedDto adCreatedDto);

    AdDto getById(Long id);

    AdFullInformationDto getFullInformationAdById(Long id);

    List<AdDto> getAll();

    List<AdShortInformationDto> getAllShortInformationAds(int pageNumber, int adsPerPage);

    AdDto update(AdUpdatedDto adUpdatedDto);

    void delete(Long id);

    void deletePictureFromAdById(Long id);

    AdDto updateAllowedFields(AdPatchedDto adPatchedDto);
}
