package com.edik.car.api.repository.projection;

import com.edik.car.api.dao.model.Condition;
import com.edik.car.api.dao.model.Picture;

import java.time.LocalDateTime;
import java.util.List;

public interface AdExtended {

    Long getAdId();

    Integer getYear();

    String getBrand();

    String getModel();

    Condition getCondition();

    Long getMileage();

    LocalDateTime getCreationTime();

    UserLimited getUser();

    List<Picture> getPictures();

    @FunctionalInterface
    interface UserLimited {

        UserInformationLimited getUserInformation();

        @FunctionalInterface
        interface UserInformationLimited {

            String getName();
        }
    }
}
