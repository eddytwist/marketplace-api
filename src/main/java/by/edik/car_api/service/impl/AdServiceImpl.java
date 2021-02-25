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
    public AdDto create(CreatedAdDto createdAdDto) {
        Ad ad = AdMapper.createdAdDtoToAd(createdAdDto);
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
    
    public List<AdShortInformationDto> getAllShortInformationAds() {
        return adDao.getAllShortInformationAds();
    }

    @Override
    public AdDto update(UpdatedAdDto updatedAdDto) {
        adDao.update(AdMapper.updatedAdDtoToAd(updatedAdDto));
        return getById(updatedAdDto.getAdId());
    }

    @Override
    public void delete(Long id) {
        adDao.delete(id);
    }

    public AdDto updateAllowedFields (PatchedAdDto patchedAdDto) {
        adDao.updateAllowedFields(AdMapper.patchedAdDtoToAd(patchedAdDto));
        return getById(patchedAdDto.getAdId());
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
