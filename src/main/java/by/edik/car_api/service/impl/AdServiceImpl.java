package by.edik.car_api.service.impl;

import by.edik.car_api.dao.AdDao;
import by.edik.car_api.dao.model.Ad;
import by.edik.car_api.service.AbstractService;
import by.edik.car_api.service.AdService;
import by.edik.car_api.service.exception.ServiceFailedException;
import by.edik.car_api.web.dto.AdCreatedDto;
import by.edik.car_api.web.dto.AdDto;
import by.edik.car_api.web.dto.AdFullInformationDto;
import by.edik.car_api.web.dto.AdPatchedDto;
import by.edik.car_api.web.dto.AdShortInformationDto;
import by.edik.car_api.web.dto.AdUpdatedDto;
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
    public AdDto create(AdCreatedDto adCreatedDto) {
        AdDto adDto;

        try {
            startTransaction();

            Ad ad = AdMapper.createdAdDtoToAd(adCreatedDto);
            adDto = AdMapper.adToAdDto(adDao.create(ad));

            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + adCreatedDto, e);
        }

        return adDto;
    }

    @Override
    public AdDto getById(Long id) {
        AdDto adDto;

        try {
            startTransaction();

            adDto = AdMapper.adToAdDto(adDao.getById(id));

            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Ad with id: " + id, e);
        }

        return adDto;
    }

    @Override
    public AdFullInformationDto getFullInformationAdById(Long id) {
        AdFullInformationDto adFullInformationDto;

        try {
            startTransaction();

            adFullInformationDto = adDao.getFullInformationAdById(id);

            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Ad with id: " + id, e);
        }

        return adFullInformationDto;
    }

    @Override
    public List<AdDto> getAll() {
        List<AdDto> ads;

        try {
            startTransaction();

            ads = adDao.getAll().stream()
                .map(AdMapper::adToAdDto)
                .collect(Collectors.toList());

            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Ads.", e);
        }

        return ads;
    }

    @Override
    public List<AdShortInformationDto> getAllShortInformationAds(int pageNumber, int adsPerPage) {
        List<AdShortInformationDto> paginatedAds;

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
    public AdDto update(AdUpdatedDto adUpdatedDto) {
        AdDto adDto;

        try {
            startTransaction();

            adDao.update(AdMapper.updatedAdDtoToAd(adUpdatedDto));
            adDto = getById(adUpdatedDto.getAdId());

            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't update Ad: " + adUpdatedDto, e);
        }

        return adDto;
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
    public AdDto updateAllowedFields(AdPatchedDto adPatchedDto) {
        AdDto adDto;

        try {
            startTransaction();

            adDao.updateAllowedFields(AdMapper.patchedAdDtoToAd(adPatchedDto));
            adDto = getById(adPatchedDto.getAdId());

            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't update Ad: " + adPatchedDto, e);
        }

        return adDto;
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
