package com.edik.car.api.dao;

import com.edik.car.api.dao.db.EntityManagerProvider;
import com.edik.car.api.dao.dto.AdShortInformationService;
import com.edik.car.api.dao.model.Ad;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "adDao")
public final class AdDao extends AbstractDao<Ad> {

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

    public Ad findAdByIdWithPics(Long id) {
        return EntityManagerProvider.getEntityManager()
            .createQuery("select ad" +
                " from Ad ad" +
                " left join fetch ad.pictures" +
                " where ad.adId = :id", Ad.class)
            .setParameter("id", id)
            .getSingleResult();
    }
}
