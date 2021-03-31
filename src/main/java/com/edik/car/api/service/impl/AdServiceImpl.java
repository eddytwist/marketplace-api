package com.edik.car.api.service.impl;

import com.edik.car.api.dao.model.Ad;
import com.edik.car.api.dao.model.Picture;
import com.edik.car.api.dao.model.User;
import com.edik.car.api.repository.AdRepository;
import com.edik.car.api.repository.PictureRepository;
import com.edik.car.api.repository.UserRepository;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.AdService;
import com.edik.car.api.service.exception.ServiceEntityNotFoundException;
import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.PatchAdRequest;
import com.edik.car.api.web.dto.request.UpdateAdRequest;
import com.edik.car.api.web.dto.response.AdResponse;
import com.edik.car.api.web.dto.response.AdWithUserInfoAndPhonesAndPicturesResponse;
import com.edik.car.api.web.dto.response.AdWithUserInfoAndPicsNumberResponse;
import com.edik.car.api.web.mapper.AdMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("adService")
@RequiredArgsConstructor
public class AdServiceImpl extends AbstractService implements AdService {

    private final AdRepository adRepository;
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AdResponse create(CreateAdRequest createAdRequest) {
        Ad adToCreate = AdMapper.toAd(createAdRequest)
            .setUser(userRepository
                .findById(createAdRequest.getUserId())
                .orElseThrow()
            );

        Ad createdAd = adRepository.save(adToCreate);

        return AdMapper.toAdResponse(createdAd);
    }

    @Override
    @Transactional
    public AdResponse getById(Long id) {
        Ad ad = adRepository.findById(id)
            .orElseThrow(() -> new ServiceEntityNotFoundException("Ad", id));
        return AdMapper.toAdResponse(ad);
    }

    @Override
    @Transactional
    public AdWithUserInfoAndPhonesAndPicturesResponse getAdWithUserInfoAndPhonesAndPicturesByAdId(Long id) {
        Ad adToResponse = adRepository.findOneWithPicturesByAdId(id)
            .orElseThrow(() -> new ServiceEntityNotFoundException("Ad", id));

        User userToResponse = userRepository.findOneWithUserInfoAndUserPhonesByUserId(adToResponse.getUser().getUserId())
            .orElseThrow(() -> new ServiceEntityNotFoundException("User", adToResponse.getUser().getUserId()));

        return AdMapper.toAdFullInformationResponse(adToResponse, userToResponse);
    }

    @Override
    @Transactional
    public List<AdResponse> getAll() {
        return adRepository.findAll()
            .stream()
            .map(AdMapper::toAdResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AdWithUserInfoAndPicsNumberResponse> getAllAdsWithPageAndSize(int pageNumber, int adsPerPage) {

        Pageable sortedByCreationTime = PageRequest.of(pageNumber, adsPerPage, Sort.by("creation_time"));

        return adRepository.findAllWithPicturesAndUserInformation(sortedByCreationTime)
            .stream()
            .map(AdMapper::toAdShortInformationResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AdResponse update(UpdateAdRequest updateAdRequest) {
        Ad foundedAd = adRepository.findById(updateAdRequest.getAdId())
            .orElseThrow(() -> new ServiceEntityNotFoundException("Ad", updateAdRequest.getAdId()));

        AdMapper.updateAdFields(foundedAd, updateAdRequest);
        Ad updatedAd = adRepository.save(foundedAd);

        return AdMapper.toAdResponse(updatedAd);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        adRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deletePictureByAdIdAndPictureId(Long id) {
        Picture pictureToDelete = pictureRepository.findOneWithAdByPictureId(id)
            .orElseThrow(() -> new ServiceEntityNotFoundException("Picture", id));

        Ad foundedAd = adRepository.findById(pictureToDelete.getAd().getAdId())
            .orElseThrow(() -> new ServiceEntityNotFoundException("Ad", id));

        foundedAd.removePicture(pictureToDelete);
        foundedAd.setEditingTime(LocalDateTime.now());

        adRepository.save(foundedAd);
    }

    @Override
    @Transactional
    public AdResponse updateYearBrandModelMileageEngineVolumeEnginePower(PatchAdRequest patchAdRequest) {
        Ad foundedAd = adRepository.findById(patchAdRequest.getAdId())
            .orElseThrow(() -> new ServiceEntityNotFoundException("Ad", patchAdRequest.getAdId()));

        AdMapper.updateAllowedAdFields(foundedAd, patchAdRequest);
        Ad patchedAd = adRepository.save(foundedAd);

        return AdMapper.toAdResponse(patchedAd);
    }
}
