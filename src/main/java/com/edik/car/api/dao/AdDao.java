package com.edik.car.api.dao;

import com.edik.car.api.dao.db.EntityManagerProvider;
import com.edik.car.api.dao.dto.AdShortInformationService;
import com.edik.car.api.dao.model.Ad;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
                " ad.pictures.size" +
                ")" +
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
