package com.edik.car.api.dao;

import com.edik.car.api.dao.db.EntityManagerProvider;
import com.edik.car.api.dao.dto.AdShortInformationService;
import com.edik.car.api.dao.model.Ad;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdDao extends AbstractDao<Ad> {

    private static volatile AdDao adDaoInstance;

    @Override
    public Class<Ad> getEntityType() {
        return Ad.class;
    }

    public List<AdShortInformationService> getAllShortInformationAds(int pageNumber, int limit) {
        return EntityManagerProvider.getEntityManager()
            .createQuery("select new com.edik.car.api.dao.dto.AdShortInformationService(" +
                " ad.adId," +
                " ad.year," +
                " ad.brand," +
                " ad.model," +
                " ad.condition," +
                " ad.mileage," +
                " ad.creationTime," +
                " ui.name," +
                " ad.pictures.size)" +
                " from Ad ad" +
                " left join UserInformation ui on ad.user.userId = ui.userId" +
                " order by ad.creationTime desc", AdShortInformationService.class)
            .setFirstResult((pageNumber - 1) * limit)
            .setMaxResults(limit)
            .getResultList();
    }

    public Ad getAdToResponse(Long id) {
        return (Ad) EntityManagerProvider.getEntityManager()
            .createQuery("select ad" +
                " from Ad ad" +
                " left join fetch ad.pictures" +
                " where ad.adId = :id")
            .setParameter("id", id)
            .getSingleResult();
    }

//    public AdFullInformationResponse getFullInformationAdById(long id) {
//        return EntityManagerProvider.getEntityManager()
//            .createQuery("select new com.edik.car.api.web.dto.response.AdFullInformationResponse(" +
//                " ad.adId," +
//                " ad.year," +
//                " ad.brand," +
//                " ad.model," +
//                " ad.engineVolume," +
//                " ad.engineVolume," +
//                " ad.condition," +
//                " ad.mileage," +
//                " ui.name," +
//                " pics" +
//                " ad.creationTime," +
//                " ad.editingTime" +
//                ")" +
//                " from Ad ad" +
//                " left join UserInformation ui on ad.user.userId = ui.userId" +
//                " join fetch ad.pictures pics" +
//                " where ad.adId = :id", AdFullInformationResponse.class)
//            .setParameter("id", id)
//            .getSingleResult();
//    }


    public List<String> getAllPicturesByAdId(Long id) {
        return EntityManagerProvider.getEntityManager()
            .createQuery("select p.reference from Picture p" +
                " where p.ad.adId = :id", String.class)
            .setParameter("id", id)
            .getResultList();
    }

    public void updateAdEditingTimeByPictureId(Long id) {
        EntityManagerProvider.getEntityManager()
            .createQuery("update Ad ad set ad.editingTime = :editingTime" +
                " where ad.adId = (" +
                " select p.ad.adId from Picture p" +
                " where p.pictureId = :id)")
            .setParameter("editingTime", LocalDateTime.now())
            .setParameter("id", id);
    }

    public Ad updateAllowedFields(Ad ad) {
        return EntityManagerProvider.getEntityManager()
            .createQuery("update Ad ad set" +
                " ad.year = :year, ad.brand = :brand, ad.model = :model, ad.engineVolume = :engineVolume, ad.mileage = :mileage, ad.enginePower = :enginePower, ad.editingTime = :editingTime" +
                " where ad.adId = :id", Ad.class)
            .setParameter("year", ad.getYear())
            .setParameter("brand", ad.getBrand())
            .setParameter("model", ad.getModel())
            .setParameter("engineVolume", ad.getEngineVolume())
            .setParameter("mileage", ad.getMileage())
            .setParameter("enginePower", ad.getEnginePower())
            .setParameter("editingTime", LocalDateTime.now())
            .setParameter("id", ad.getAdId())
            .getSingleResult();
    }


    public static AdDao getInstance() {
        AdDao localInstance = adDaoInstance;

        if (localInstance == null) {

            synchronized (AdDao.class) {
                localInstance = adDaoInstance;

                if (localInstance == null) {
                    adDaoInstance = localInstance = new AdDao();
                }
            }
        }

        return localInstance;
    }
}
