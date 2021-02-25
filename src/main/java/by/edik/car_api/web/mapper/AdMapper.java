package by.edik.car_api.web.mapper;

import by.edik.car_api.model.Ad;
import by.edik.car_api.web.dto.*;

import java.time.LocalDateTime;

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

    public static Ad createdAdDtoToAd(CreatedAdDto createdAdDto) {
        return Ad.builder()
                .userId(createdAdDto.getUserId())
                .year(createdAdDto.getYear())
                .brand(createdAdDto.getBrand())
                .model(createdAdDto.getModel())
                .engineVolume(createdAdDto.getEngineVolume())
                .condition(createdAdDto.getCondition())
                .mileage(createdAdDto.getMileage())
                .enginePower(createdAdDto.getEnginePower())
                .creationTime(createdAdDto.getCreationTime())
                .editingTime(createdAdDto.getEditingTime())
                .build();
    }

    public static Ad updatedAdDtoToAd(UpdatedAdDto updatedAdDto) {
        return Ad.builder()
                .adId(updatedAdDto.getAdId())
                .userId(updatedAdDto.getUserId())
                .year(updatedAdDto.getYear())
                .brand(updatedAdDto.getBrand())
                .model(updatedAdDto.getModel())
                .engineVolume(updatedAdDto.getEngineVolume())
                .condition(updatedAdDto.getCondition())
                .mileage(updatedAdDto.getMileage())
                .enginePower(updatedAdDto.getEnginePower())
                .creationTime(updatedAdDto.getCreationTime())
                .editingTime(updatedAdDto.getEditingTime())
                .build();
    }

    public static Ad patchedAdDtoToAd(PatchedAdDto patchedAdDto) {
        return Ad.builder()
                .adId(patchedAdDto.getAdId())
                .year(patchedAdDto.getYear())
                .brand(patchedAdDto.getBrand())
                .model(patchedAdDto.getModel())
                .engineVolume(patchedAdDto.getEngineVolume())
                .mileage(patchedAdDto.getMileage())
                .enginePower(patchedAdDto.getEnginePower())
                .editingTime(LocalDateTime.now())
                .build();
    }
}


