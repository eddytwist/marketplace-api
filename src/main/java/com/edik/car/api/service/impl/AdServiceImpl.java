package com.edik.car.api.service.impl;

import com.edik.car.api.dao.AdDao;
import com.edik.car.api.dao.PictureDao;
import com.edik.car.api.dao.UserDao;
import com.edik.car.api.dao.dto.AdWithUserInfoAndPicsNumberService;
import com.edik.car.api.dao.model.Ad;
import com.edik.car.api.dao.model.Picture;
import com.edik.car.api.dao.model.User;
import com.edik.car.api.service.AbstractService;
import com.edik.car.api.service.AdService;
import com.edik.car.api.service.exception.ServiceEntityNotFoundException;
import com.edik.car.api.service.exception.ServiceFailedException;
import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.PatchAdRequest;
import com.edik.car.api.web.dto.request.UpdateAdRequest;
import com.edik.car.api.web.dto.response.AdResponse;
import com.edik.car.api.web.dto.response.AdWithUserInfoAndPhonesAndPicturesResponse;
import com.edik.car.api.web.dto.response.AdWithUserInfoAndPicsNumberResponse;
import com.edik.car.api.web.mapper.AdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("adService")
public final class AdServiceImpl extends AbstractService implements AdService {

    @Autowired
    private AdDao adDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PictureDao pictureDao;

    @Override
    public AdResponse create(CreateAdRequest createAdRequest) {
        Ad adToCreate = AdMapper.toAd(createAdRequest);
        Ad createdAd;

        try {
            begin();

            adToCreate.setUser(userDao.findById(createAdRequest.getUserId()));
            createdAd = adDao.save(adToCreate);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + createAdRequest, e);
        }

        return AdMapper.toAdResponse(createdAd);
    }

    @Override
    public AdResponse getById(Long id) {
        Ad ad;

        try {
            begin();

            ad = adDao.findById(id);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Ad with id: " + id, e);
        }

        return AdMapper.toAdResponse(ad);
    }

    @Override
    public AdWithUserInfoAndPhonesAndPicturesResponse getAdWithUserInfoAndPhonesAndPicturesByAdId(Long id) {
        Ad adToResponse;
        User userToResponse;

        try {
            begin();

            adToResponse = adDao.findAdByIdWithPics(id);
            userToResponse = userDao.findByIdWithUserInfoAndPhones(adToResponse.getUser().getUserId());

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Ad with id: " + id, e);
        }

        return AdMapper.toAdFullInformationResponse(adToResponse, userToResponse);
    }

    @Override
    public List<AdResponse> getAll() {
        List<Ad> ads;

        try {
            begin();

            ads = adDao.findAll();

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Ads.", e);
        }

        return ads
            .stream()
            .map(AdMapper::toAdResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<AdWithUserInfoAndPicsNumberResponse> getAllAdsWithPageAndSize(int pageNumber, int adsPerPage) {
        List<AdWithUserInfoAndPicsNumberService> paginatedAds;

        try {
            begin();

            paginatedAds = adDao.getAllShortInformationAds(pageNumber, adsPerPage);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Ads.", e);
        }

        return paginatedAds
            .stream()
            .map(AdMapper::toAdShortInformationResponse)
            .collect(Collectors.toList());
    }

    @Override
    public AdResponse update(UpdateAdRequest updateAdRequest) {
        Ad updatedAd;

        try {
            begin();

            Ad foundedAd = adDao.findById(updateAdRequest.getAdId());

            if (foundedAd != null) {
                AdMapper.updateAdFields(foundedAd, updateAdRequest);
                updatedAd = adDao.update(foundedAd);
            } else {
                throw new ServiceEntityNotFoundException("Ad", updateAdRequest.getAdId());
            }

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't update Ad: " + updateAdRequest, e);
        }

        return AdMapper.toAdResponse(updatedAd);
    }

    @Override
    public void delete(Long id) {
        try {
            begin();

            adDao.delete(id);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't delete Ad id: " + id, e);
        }
    }

    @Override
    public void deletePictureByAdIdAndPictureId(Long id) {
        try {
            begin();

            Picture pictureToDelete = pictureDao.findByIdWithAd(id);
            Ad foundedAd = adDao.findById(pictureToDelete.getAd().getAdId());

            foundedAd.removePicture(pictureToDelete);
            foundedAd.setEditingTime(LocalDateTime.now());

            adDao.update(foundedAd);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't delete Picture from Ad by Picture id: " + id, e);
        }
    }

    @Override
    public AdResponse updateYearBrandModelMileageEngineVolumeEnginePower(PatchAdRequest patchAdRequest) {
        Ad patchedAd;

        try {
            begin();

            Ad foundedAd = adDao.findById(patchAdRequest.getAdId());

            if (foundedAd != null) {
                AdMapper.updateAllowedAdFields(foundedAd, patchAdRequest);
                patchedAd = adDao.update(foundedAd);
            } else {
                throw new ServiceEntityNotFoundException("Ad", patchAdRequest.getAdId());
            }

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't update Ad: " + patchAdRequest, e);
        }

        return AdMapper.toAdResponse(patchedAd);
    }
}
