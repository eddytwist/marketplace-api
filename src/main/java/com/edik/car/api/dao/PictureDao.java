package com.edik.car.api.dao;

import com.edik.car.api.dao.db.EntityManagerProvider;
import com.edik.car.api.dao.model.Picture;
import org.springframework.stereotype.Repository;

@Repository(value = "pictureDao")
public final class PictureDao extends AbstractDao<Picture> {

    @Override
    public Class<Picture> getEntityType() {
        return Picture.class;
    }

    public Picture findByIdWithAd(Long id) {
        return EntityManagerProvider.getEntityManager()
            .createQuery("select pic" +
                " from Picture pic" +
                " left join fetch pic.ad" +
                " where pic.pictureId = :id", Picture.class)
            .setParameter("id", id)
            .getSingleResult();
    }
}
