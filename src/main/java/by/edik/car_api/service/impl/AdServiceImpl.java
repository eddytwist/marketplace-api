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
        Ad adToCreate = AdMapper.createdAdDtoToAd(adCreatedDto);
        Ad adToReturn;

        try {
            startTransaction();
            adToReturn = adDao.create(adToCreate);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Creating failed: " + adCreatedDto, e);
        }

        return AdMapper.adToAdDto(adToReturn);
    }

    @Override
    public AdDto getById(Long id) {
        Ad adToReturn;

        try {
            startTransaction();
            adToReturn = adDao.getById(id);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Ad with id: " + id, e);
        }

        return AdMapper.adToAdDto(adToReturn);
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
        List<Ad> adsToReturn;

        try {
            startTransaction();
            adsToReturn = adDao.getAll();
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't find Ads.", e);
        }

        return adsToReturn.stream()
            .map(AdMapper::adToAdDto)
            .collect(Collectors.toList());
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
        Ad adToUpdate = AdMapper.updatedAdDtoToAd(adUpdatedDto);

        try {
            startTransaction();
            adDao.update(adToUpdate);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't update Ad: " + adUpdatedDto, e);
        }

        return getById(adToUpdate.getAdId());
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
        Ad adToUpdate = AdMapper.patchedAdDtoToAd(adPatchedDto);

        try {
            startTransaction();
            adDao.updateAllowedFields(adToUpdate);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new ServiceFailedException("Can't update Ad: " + adPatchedDto, e);
        }

        return getById(adPatchedDto.getAdId());
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
