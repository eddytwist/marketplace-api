package com.edik.car.api.service;

import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.PatchAdRequest;
import com.edik.car.api.web.dto.request.UpdateAdRequest;
import com.edik.car.api.web.dto.response.AdFullInformationResponse;
import com.edik.car.api.web.dto.response.AdResponse;
import com.edik.car.api.web.dto.response.AdShortInformationResponse;

import java.util.List;

public interface AdService {

    AdResponse create(CreateAdRequest adCreatedDto);

    AdResponse getById(Long id);

    AdFullInformationResponse getFullInformationAdById(Long id);

    List<AdResponse> getAll();

    List<AdShortInformationResponse> getAllShortInformationAds(int pageNumber, int adsPerPage);

    AdResponse update(UpdateAdRequest updateAdRequest);

    void delete(Long id);

    void deletePictureFromAdById(Long id);

    AdResponse updateAllowedFields(PatchAdRequest patchAdRequest);
}
