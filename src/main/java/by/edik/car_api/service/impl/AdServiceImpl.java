package by.edik.car_api.service.impl;

import by.edik.car_api.dao.AdDao;
import by.edik.car_api.model.Ad;
import by.edik.car_api.service.AdService;
import by.edik.car_api.web.dto.*;
import by.edik.car_api.web.mapper.AdMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AdServiceImpl implements AdService {

    private static volatile AdServiceImpl adServiceImplInstance;

    private final AdDao adDao = AdDao.getInstance();

    @Override
    public AdDto create(AdCreatedDto adCreatedDto) {
        Ad ad = AdMapper.createdAdDtoToAd(adCreatedDto);
        return AdMapper.adToAdDto(adDao.create(ad));
    }

    @Override
    public AdDto getById(Long id) {
        return AdMapper.adToAdDto(adDao.getById(id));
    }
    
    public AdFullInformationDto getFullInformationAdById(Long id) {
        return adDao.getFullInformationAdById(id);
    }

    @Override
    public List<AdDto> getAll() {
        return adDao.getAll().stream()
                .map(AdMapper::adToAdDto)
                .collect(Collectors.toList());
    }
    
    public List<AdShortInformationDto> getAllShortInformationAds(int pageNumber, int adsPerPage) {
        int offset = (pageNumber-1) * adsPerPage;
        return adDao.getAllShortInformationAds(adsPerPage,offset);
    }

    @Override
    public AdDto update(AdUpdatedDto adUpdatedDto) {
        adDao.update(AdMapper.updatedAdDtoToAd(adUpdatedDto));
        return getById(adUpdatedDto.getAdId());
    }

    @Override
    public void delete(Long id) {
        adDao.delete(id);
    }

    public AdDto updateAllowedFields(AdPatchedDto adPatchedDto) {
        adDao.updateAllowedFields(AdMapper.patchedAdDtoToAd(adPatchedDto));
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
