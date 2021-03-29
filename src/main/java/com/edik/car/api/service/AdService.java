package com.edik.car.api.service;

import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.PatchAdRequest;
import com.edik.car.api.web.dto.request.UpdateAdRequest;
import com.edik.car.api.web.dto.response.AdResponse;
import com.edik.car.api.web.dto.response.AdWithUserInfoAndPhonesAndPicturesResponse;
import com.edik.car.api.web.dto.response.AdWithUserInfoAndPicsNumberResponse;

import java.util.List;

public interface AdService {

    AdResponse create(CreateAdRequest adCreatedDto);

    AdResponse getById(Long id);

    AdWithUserInfoAndPhonesAndPicturesResponse getAdWithUserInfoAndPhonesAndPicturesByAdId(Long id);

    List<AdResponse> getAll();

    List<AdWithUserInfoAndPicsNumberResponse> getAllAdsWithPageAndSize(int pageNumber, int adsPerPage);

    AdResponse update(UpdateAdRequest updateAdRequest);

    void delete(Long id);

    void deletePictureByAdIdAndPictureId(Long id);

    AdResponse updateYearBrandModelMileageEngineVolumeEnginePower(PatchAdRequest patchAdRequest);
}
