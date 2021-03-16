package by.edik.car_api.dao;

import by.edik.car_api.dao.model.Ad;
import by.edik.car_api.web.dto.response.AdFullInformationResponse;
import by.edik.car_api.web.dto.response.AdShortInformationResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static by.edik.car_api.dao.db.EntityManagerProvider.getEntityManager;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdDaoHiba extends AbstractDaoHiba<Ad> {

    private static volatile AdDaoHiba adDaoInstance;

    @Override
    public Class<Ad> getEntityType() {
        return Ad.class;
    }

    public List<AdShortInformationResponse> getAllShortInformationAds(int pageNumber, int limit) {
        return getEntityManager()
            .createQuery("select ad.adId, ad.year, ad.brand, ad.model, ad.condition, ad.mileage, ad.creationTime, ui.name, count(pic.reference)" +
                " from Ad ad" +
                " join fetch UserInformation ui" +
                " left join Picture pic" +
                " group by  ad.adId, ui.name, ad.creationTime" +
                " order by ad.creationTime desc", AdShortInformationResponse.class)
            .setFirstResult((pageNumber - 1) * limit)
            .setMaxResults(limit)
            .getResultList();
    }

    public AdFullInformationResponse getFullInformationAdById(long id) {
        AdFullInformationResponse adFullInformationResponse;
        adFullInformationResponse = getEntityManager()
            .createQuery("select ad.adId, ad.year, ad.brand, ad.model,  ad.engineVolume, ad.engineVolume, ad.condition, ad.mileage, ui.name, up.phoneNumber, ad.creationTime, ad.editingTime" +
                " from Ad ad" +
                " join fetch UserInformation ui" +
                " left join UserPhone up" +
                " where ad.adId = " + id, AdFullInformationResponse.class)
            .getSingleResult();
        adFullInformationResponse.setPictureReferences(getAllPicturesByAdId(id));
        return adFullInformationResponse;
    }

    public List<String> getAllPicturesByAdId(Long id) {
        return getEntityManager()
            .createQuery("select p.reference from Picture p" +
                " where p.ad.adId = " + id, String.class)
            .getResultList();
    }

    public void updateAdEditingTimeByPictureId(Long id) {

    }

    public void updateAllowedFields(Ad ad) {

    }


    public static AdDaoHiba getInstance() {
        AdDaoHiba localInstance = adDaoInstance;

        if (localInstance == null) {

            synchronized (AdDaoHiba.class) {
                localInstance = adDaoInstance;

                if (localInstance == null) {
                    adDaoInstance = localInstance = new AdDaoHiba();
                }
            }
        }

        return localInstance;
    }
}
