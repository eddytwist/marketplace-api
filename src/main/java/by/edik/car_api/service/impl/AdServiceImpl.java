package by.edik.car_api.service.impl;

import by.edik.car_api.dao.AdDao;
import by.edik.car_api.dao.model.Ad;
import by.edik.car_api.service.AbstractService;
import by.edik.car_api.service.AdService;
import by.edik.car_api.service.exception.ServiceFailedException;
import by.edik.car_api.web.dto.request.CreateAdRequest;
import by.edik.car_api.web.dto.request.PatchAdRequest;
import by.edik.car_api.web.dto.request.UpdateAdRequest;
import by.edik.car_api.web.dto.response.AdFullInformationResponse;
import by.edik.car_api.web.dto.response.AdResponse;
import by.edik.car_api.web.dto.response.AdShortInformationResponse;
import by.edik.car_api.web.mapper.AdMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdServiceImpl extends AbstractService implements AdService {

    private static volatile AdServiceImpl adServiceImplInstance;

    private final PictureServiceImpl pictureService = PictureServiceImpl.getInstance();

    private final AdDao adDao = AdDao.getInstance();

    @Override
    public AdResponse create(CreateAdRequest adCreatedDto) {
        Ad adToCreate = AdMapper.toAd(adCreatedDto);
        Ad createdAd;

        try {
            startTransaction();
            createdAd = adDao.save(adToCreate);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + adCreatedDto, e);
        }

        return AdMapper.toAdResponse(createdAd);
    }

    @Override
    public AdResponse getById(Long id) {
        Ad ad;

        try {
            startTransaction();
            ad = adDao.findById(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Ad with id: " + id, e);
        }

        return AdMapper.toAdResponse(ad);
    }

    @Override
    public AdFullInformationResponse getFullInformationAdById(Long id) {
        AdFullInformationResponse adFullInformationResponse;

        try {
            startTransaction();
            adFullInformationResponse = adDao.getFullInformationAdById(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Ad with id: " + id, e);
        }

        return adFullInformationResponse;
    }

    @Override
    public List<AdResponse> getAll() {
        List<Ad> ads;

        try {
            startTransaction();
            ads = adDao.findAll();
            commit();
        } catch (SQLException e) {
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
            startTransaction();
            paginatedAds = adDao.getAllShortInformationAds(pageNumber, adsPerPage);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Ads.", e);
        }

        return paginatedAds;
    }

    @Override
    public AdResponse update(UpdateAdRequest updateAdRequest) {
        Ad ad = AdMapper.toAd(updateAdRequest);

        try {
            startTransaction();
            adDao.update(ad);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't update Ad: " + updateAdRequest, e);
        }

        return getById(ad.getAdId());
    }

    @Override
    public void delete(Long id) {
        try {
            startTransaction();
            adDao.delete(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't delete Ad id: " + id, e);
        }
    }

    @Override
    public void deletePictureFromAdById(Long id) {
        try {
            startTransaction();

            adDao.updateAdEditingTimeByPictureId(id);
            pictureService.delete(id);

            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't delete Picture from Ad by Picture id: " + id, e);
        }
    }

    @Override
    public AdResponse updateAllowedFields(PatchAdRequest patchAdRequest) {
        Ad ad = AdMapper.toAd(patchAdRequest);

        try {
            startTransaction();
            adDao.updateAllowedFields(ad);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't update Ad: " + patchAdRequest, e);
        }

        return getById(patchAdRequest.getAdId());
    }

    public static AdServiceImpl getInstance() {
        AdServiceImpl localInstance = adServiceImplInstance;

        if (localInstance == null) {

            synchronized (AdServiceImpl.class) {
                localInstance = adServiceImplInstance;

                if (localInstance == null) {
                    adServiceImplInstance = localInstance = new AdServiceImpl();
                }
            }
        }

        return localInstance;
    }
}
