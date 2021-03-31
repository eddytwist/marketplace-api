package com.edik.car.api.web.controller;

import com.edik.car.api.service.AdService;
import com.edik.car.api.service.PictureService;
import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.PatchAdRequest;
import com.edik.car.api.web.dto.request.UpdateAdRequest;
import com.edik.car.api.web.dto.response.AdResponse;
import com.edik.car.api.web.dto.response.AdWithUserInfoAndPhonesAndPicturesResponse;
import com.edik.car.api.web.dto.response.AdWithUserInfoAndPicsNumberResponse;
import com.edik.car.api.web.dto.response.PictureResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdsController {

    public static final String DEFAULT_ADS_PER_PAGE = "10";
    public static final String DEFAULT_PAGE_NUMBER = "1";

    private final AdService adService;
    private final PictureService pictureService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdWithUserInfoAndPicsNumberResponse>> getAds(
        @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(value = "size", defaultValue = DEFAULT_ADS_PER_PAGE) int size
    ) {
        return ResponseEntity.ok(adService.getAllAdsWithPageAndSize(page, size));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdResponse> createAd(@RequestBody CreateAdRequest createAdRequest) {
        return ResponseEntity.ok(adService.create(createAdRequest));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdResponse> updateAd(@RequestBody UpdateAdRequest updateAdRequest) {
        return ResponseEntity.ok(adService.update(updateAdRequest));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdResponse> patchAd(@RequestBody PatchAdRequest patchAdRequest) {
        return ResponseEntity.ok(adService.updateYearBrandModelMileageEngineVolumeEnginePower(patchAdRequest));
    }

    @GetMapping(value = "/{adId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdWithUserInfoAndPhonesAndPicturesResponse> getAd(@PathVariable Long adId) {
        return ResponseEntity.ok(adService.getAdWithUserInfoAndPhonesAndPicturesByAdId(adId));
    }

    @DeleteMapping("/{adId}")
    public ResponseEntity<Void> deleteAd(@PathVariable long adId) {
        adService.delete(adId);

        return ResponseEntity.noContent()
            .build();
    }

    @GetMapping(value = "/{adId}/pictures/{pictureId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PictureResponse> getPicture(@PathVariable long adId, @PathVariable long pictureId) {
        return ResponseEntity.ok(pictureService.getById(pictureId));
    }

    //TODO!! add adId to delete method.
    @DeleteMapping("/{adId}/pictures/{pictureId}")
    public ResponseEntity<Void> deletePicture(@PathVariable long adId, @PathVariable long pictureId) {
        adService.deletePictureByAdIdAndPictureId(pictureId);

        return ResponseEntity.noContent()
            .build();
    }
}
