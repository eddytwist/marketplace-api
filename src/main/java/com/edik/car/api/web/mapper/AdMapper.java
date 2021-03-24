package com.edik.car.api.web.mapper;

import com.edik.car.api.dao.dto.AdShortInformationService;
import com.edik.car.api.dao.model.Ad;
import com.edik.car.api.dao.model.Picture;
import com.edik.car.api.dao.model.User;
import com.edik.car.api.dao.model.UserPhone;
import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.PatchAdRequest;
import com.edik.car.api.web.dto.request.UpdateAdRequest;
import com.edik.car.api.web.dto.response.AdFullInformationResponse;
import com.edik.car.api.web.dto.response.AdResponse;
import com.edik.car.api.web.dto.response.AdShortInformationResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public static AdFullInformationResponse toAdFullInformationResponse(Ad ad, User user) {
        if (ad == null || user == null) {
            return null;
        }

        return AdFullInformationResponse.builder()
            .adId(ad.getAdId())
            .year(ad.getYear())
            .brand(ad.getBrand())
            .model(ad.getModel())
            .engineVolume(ad.getEngineVolume())
            .enginePower(ad.getEnginePower())
            .condition(ad.getCondition())
            .ownerName(
                getOwnerName(user)
            )
            .mileage(ad.getMileage())
            .userPhones(
                getPhones(user)
            )
            .pictures(
                getPictures(ad)
            )
            .creationTime(ad.getCreationTime())
            .editingTime(ad.getEditingTime())
            .build();
    }

    private static String getOwnerName(User user) {
        if (user.getUserInformation() == null) {
            return null;
        }

        return user.getUserInformation().getName();
    }

    public static AdShortInformationResponse toAdShortInformationResponse(AdShortInformationService ad) {
        if (ad == null) {
            return null;
        }

        return AdShortInformationResponse.builder()
            .adId(ad.getAdId())
            .year(ad.getYear())
            .brand(ad.getBrand())
            .model(ad.getModel())
            .condition(ad.getCondition())
            .mileage(ad.getMileage())
            .creationTime(ad.getCreationTime())
            .ownerName(ad.getOwnerName())
            .picturesNumber(ad.getPicturesNumber())
            .build();
    }

    public static Ad toAd(CreateAdRequest request) {
        if (request == null) {
            return null;
        }

        Ad ad = Ad.builder()
            .year(request.getYear())
            .brand(request.getBrand())
            .model(request.getModel())
            .engineVolume(request.getEngineVolume())
            .condition(request.getCondition())
            .mileage(request.getMileage())
            .enginePower(request.getEnginePower())
            .build();

        setPictures(ad, request);

        return ad;
    }

    private static List<String> getPictures(Ad ad) {
        if (ad.getPictures() == null) {
            return Collections.emptyList();
        }

        return ad.getPictures()
            .stream()
            .map(Picture::getReference)
            .collect(Collectors.toList());
    }

    private static List<String> getPhones(User user) {
        if (user.getUserPhones() == null) {
            return Collections.emptyList();
        }

        return user.getUserPhones()
            .stream()
            .map(UserPhone::getPhoneNumber)
            .collect(Collectors.toList());
    }

    private static void setPictures(Ad ad, CreateAdRequest request) {
        if (request.getPictureReferences() == null) {
            return;
        }

        request.getPictureReferences()
            .stream()
            .map(PictureMapper::toPicture)
            .forEach(ad::addPicture);
    }

    private static void setPictures(Ad ad, UpdateAdRequest request) {
        if (request.getPictureReferences() == null) {
            return;
        }

        ad.getPictures().clear();

        request.getPictureReferences()
            .stream()
            .map(PictureMapper::toPicture)
            .forEach(ad::addPicture);
    }

    public static void updateAdFields(Ad foundedAd, UpdateAdRequest updateAdRequest) {
        foundedAd.setBrand(updateAdRequest.getBrand());
        foundedAd.setModel(updateAdRequest.getModel());
        foundedAd.setYear(updateAdRequest.getYear());
        foundedAd.setCondition(updateAdRequest.getCondition());
        foundedAd.setEnginePower(updateAdRequest.getEnginePower());
        foundedAd.setEngineVolume(updateAdRequest.getEngineVolume());
        foundedAd.setMileage(updateAdRequest.getMileage());
        foundedAd.setEditingTime(LocalDateTime.now());

        setPictures(foundedAd, updateAdRequest);
    }

    public static void updateAllowedAdFields(Ad foundedAd, PatchAdRequest patchAdRequest) {
        foundedAd.setBrand(patchAdRequest.getBrand());
        foundedAd.setModel(patchAdRequest.getModel());
        foundedAd.setYear(patchAdRequest.getYear());
        foundedAd.setEnginePower(patchAdRequest.getEnginePower());
        foundedAd.setEngineVolume(patchAdRequest.getEngineVolume());
        foundedAd.setMileage(patchAdRequest.getMileage());
        foundedAd.setEditingTime(LocalDateTime.now());
    }
}
