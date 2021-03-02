package by.edik.car_api.web.mapper;

import by.edik.car_api.model.Ad;
import by.edik.car_api.web.dto.AdCreatedDto;
import by.edik.car_api.web.dto.AdDto;
import by.edik.car_api.web.dto.AdPatchedDto;
import by.edik.car_api.web.dto.AdUpdatedDto;

public class AdMapper {

    public static AdDto adToAdDto(Ad ad) {
        return AdDto.builder()
                .adId(ad.getUserId())
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

    public static Ad createdAdDtoToAd(AdCreatedDto adCreatedDto) {
        return Ad.builder()
                .userId(adCreatedDto.getUserId())
                .year(adCreatedDto.getYear())
                .brand(adCreatedDto.getBrand())
                .model(adCreatedDto.getModel())
                .engineVolume(adCreatedDto.getEngineVolume())
                .condition(adCreatedDto.getCondition())
                .mileage(adCreatedDto.getMileage())
                .enginePower(adCreatedDto.getEnginePower())
                .build();
    }

    public static Ad updatedAdDtoToAd(AdUpdatedDto adUpdatedDto) {
        return Ad.builder()
                .adId(adUpdatedDto.getAdId())
                .userId(adUpdatedDto.getUserId())
                .year(adUpdatedDto.getYear())
                .brand(adUpdatedDto.getBrand())
                .model(adUpdatedDto.getModel())
                .engineVolume(adUpdatedDto.getEngineVolume())
                .condition(adUpdatedDto.getCondition())
                .mileage(adUpdatedDto.getMileage())
                .enginePower(adUpdatedDto.getEnginePower())
                .build();
    }

    public static Ad patchedAdDtoToAd(AdPatchedDto adPatchedDto) {
        return Ad.builder()
                .adId(adPatchedDto.getAdId())
                .year(adPatchedDto.getYear())
                .brand(adPatchedDto.getBrand())
                .model(adPatchedDto.getModel())
                .engineVolume(adPatchedDto.getEngineVolume())
                .mileage(adPatchedDto.getMileage())
                .enginePower(adPatchedDto.getEnginePower())
                .build();
    }
}


