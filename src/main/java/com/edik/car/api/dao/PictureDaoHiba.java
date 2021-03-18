package com.edik.car.api.dao;

import com.edik.car.api.dao.model.Picture;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PictureDaoHiba extends AbstractDaoHiba<Picture> {

    private static volatile PictureDaoHiba pictureDaoInstance;

    private static final String GET_ALL_QUERY = "SELECT * FROM pictures";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM pictures WHERE picture_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO pictures VALUES (DEFAULT, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE pictures SET reference = ? WHERE picture_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM pictures WHERE picture_id = ?";

    @Override
    public Class<Picture> getEntityType() {
        return Picture.class;
    }


    public static PictureDaoHiba getInstance() {
        PictureDaoHiba localInstance = pictureDaoInstance;

        if (localInstance == null) {

            synchronized (PictureDaoHiba.class) {
                localInstance = pictureDaoInstance;

                if (localInstance == null) {
                    pictureDaoInstance = localInstance = new PictureDaoHiba();
                }
            }
        }

        return localInstance;
    }
}
