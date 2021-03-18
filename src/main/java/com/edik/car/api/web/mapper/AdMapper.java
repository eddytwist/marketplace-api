package com.edik.car.api.web.mapper;

import com.edik.car.api.dao.model.Ad;
import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.PatchAdRequest;
import com.edik.car.api.web.dto.request.UpdateAdRequest;
import com.edik.car.api.web.dto.response.AdResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdMapper {

    public static AdResponse toAdResponse(Ad ad) {
        if (ad == null) {
            return null;
        }

        return AdResponse.builder()
            .adId(ad.getAdId())
            .year(ad.getYear())
            .brand(ad.getBrand())
            .model(ad.getModel())
            .engineVolume(ad.getEngineVolume())
            .condition(ad.getCondition())
            .mileage(ad.getMileage())
            .enginePower(ad.getEnginePower())
            .creationTime(ad.getCreationTime())
            .editingTime(ad.getEditingTime())
            .build();
    }

    public static Ad toAd(CreateAdRequest createAdRequest) {
        if (createAdRequest == null) {
            return null;
        }

        return Ad.builder()
            .year(createAdRequest.getYear())
            .brand(createAdRequest.getBrand())
            .model(createAdRequest.getModel())
            .engineVolume(createAdRequest.getEngineVolume())
            .condition(createAdRequest.getCondition())
            .mileage(createAdRequest.getMileage())
            .enginePower(createAdRequest.getEnginePower())
            .build();
    }

    public static Ad toAd(UpdateAdRequest updateAdRequest) {
        if (updateAdRequest == null) {
            return null;
        }

        return Ad.builder()
            .adId(updateAdRequest.getAdId())
//            .userId(updateAdRequest.getUserId())
            .year(updateAdRequest.getYear())
            .brand(updateAdRequest.getBrand())
            .model(updateAdRequest.getModel())
            .engineVolume(updateAdRequest.getEngineVolume())
            .condition(updateAdRequest.getCondition())
            .mileage(updateAdRequest.getMileage())
            .enginePower(updateAdRequest.getEnginePower())
            .build();
    }

    public static Ad toAd(PatchAdRequest patchAdRequest) {
        if (patchAdRequest == null) {
            return null;
        }

        return Ad.builder()
            .adId(patchAdRequest.getAdId())
            .year(patchAdRequest.getYear())
            .brand(patchAdRequest.getBrand())
            .model(patchAdRequest.getModel())
            .engineVolume(patchAdRequest.getEngineVolume())
            .mileage(patchAdRequest.getMileage())
            .enginePower(patchAdRequest.getEnginePower())
            .build();
    }
}
