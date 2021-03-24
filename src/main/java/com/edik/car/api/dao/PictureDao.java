package com.edik.car.api.dao;

import com.edik.car.api.dao.db.EntityManagerProvider;
import com.edik.car.api.dao.model.Picture;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PictureDao extends AbstractDao<Picture> {

    private static volatile PictureDao pictureDaoInstance;

    @Override
    public Class<Picture> getEntityType() {
        return Picture.class;
    }

    public Picture findByIdWithAd(Long id) {
        return (Picture) EntityManagerProvider.getEntityManager()
            .createQuery("select pic" +
                " from Picture pic" +
                " left join fetch pic.ad" +
                " where pic.pictureId = :id")
            .setParameter("id", id)
            .getSingleResult();
    }

    public static PictureDao getInstance() {
        PictureDao localInstance = pictureDaoInstance;

        if (localInstance == null) {

            synchronized (PictureDao.class) {
                localInstance = pictureDaoInstance;

                if (localInstance == null) {
                    pictureDaoInstance = localInstance = new PictureDao();
                }
            }
        }

        return localInstance;
    }
}
