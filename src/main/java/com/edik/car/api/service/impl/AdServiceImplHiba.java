package com.edik.car.api.service.impl;

import com.edik.car.api.dao.AdDaoHiba;
import com.edik.car.api.dao.model.Ad;
import com.edik.car.api.service.AbstractServiceHiba;
import com.edik.car.api.service.AdService;
import com.edik.car.api.service.exception.ServiceFailedException;
import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.PatchAdRequest;
import com.edik.car.api.web.dto.request.UpdateAdRequest;
import com.edik.car.api.web.dto.response.AdFullInformationResponse;
import com.edik.car.api.web.dto.response.AdResponse;
import com.edik.car.api.web.dto.response.AdShortInformationResponse;
import com.edik.car.api.web.mapper.AdMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdServiceImplHiba extends AbstractServiceHiba implements AdService {

    private static volatile AdServiceImplHiba adServiceImplInstance;

    private final PictureServiceImpl pictureService = PictureServiceImpl.getInstance();
    private final UserServiceImplHiba userServiceImplHiba = UserServiceImplHiba.getInstance();

    private final AdDaoHiba adDaoHiba = AdDaoHiba.getInstance();

    @Override
    public AdResponse create(CreateAdRequest createAdRequest) {
        Ad adToCreate = AdMapper.toAd(createAdRequest);
        adToCreate.setCreationTime(LocalDateTime.now());
        adToCreate.setEditingTime(LocalDateTime.now());
        adToCreate.setUser(userServiceImplHiba.getByIdForAd(createAdRequest.getUserId()));
        Ad createdAd;

        try {
            begin();
            createdAd = adDaoHiba.save(adToCreate);
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
            ad = adDaoHiba.findById(id);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Ad with id: " + id, e);
        }

        return AdMapper.toAdResponse(ad);
    }

    @Override
    public AdFullInformationResponse getFullInformationAdById(Long id) {
        AdFullInformationResponse adFullInformationResponse;

        try {
            begin();
            adFullInformationResponse = adDaoHiba.getFullInformationAdById(id);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Ad with id: " + id, e);
        }

        return adFullInformationResponse;
    }

    @Override
    public List<AdResponse> getAll() {
        List<Ad> ads;

        try {
            begin();
            ads = adDaoHiba.findAll();
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Ads.", e);
        }

        return ads.stream()
            .map(AdMapper::toAdResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<AdShortInformationResponse> getAllShortInformationAds(int pageNumber, int adsPerPage) {
        List<AdShortInformationResponse> paginatedAds;

        try {
            begin();
            paginatedAds = adDaoHiba.getAllShortInformationAds(pageNumber, adsPerPage);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't find Ads.", e);
        }

        return paginatedAds;
    }

    @Override
    public AdResponse update(UpdateAdRequest updateAdRequest) {
        Ad adToUpdate = AdMapper.toAd(updateAdRequest);
        Ad updatedAd;

        try {
            begin();
            updatedAd = adDaoHiba.update(adToUpdate);
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
            adDaoHiba.delete(id);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't delete Ad id: " + id, e);
        }
    }

    @Override
    public void deletePictureFromAdById(Long id) {
        try {
            begin();

            adDaoHiba.updateAdEditingTimeByPictureId(id);
            pictureService.delete(id);

            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't delete Picture from Ad by Picture id: " + id, e);
        }
    }

    @Override
    public AdResponse updateAllowedFields(PatchAdRequest patchAdRequest) {
        Ad ad = AdMapper.toAd(patchAdRequest);

        try {
            begin();
            adDaoHiba.updateAllowedFields(ad);
            commit();
        } catch (Exception e) {
            rollback();
            throw new ServiceFailedException("Can't update Ad: " + patchAdRequest, e);
        }

        return getById(patchAdRequest.getAdId());
    }

    public static AdServiceImplHiba getInstance() {
        AdServiceImplHiba localInstance = adServiceImplInstance;

        if (localInstance == null) {

            synchronized (AdServiceImplHiba.class) {
                localInstance = adServiceImplInstance;

                if (localInstance == null) {
                    adServiceImplInstance = localInstance = new AdServiceImplHiba();
                }
            }
        }

        return localInstance;
    }
}