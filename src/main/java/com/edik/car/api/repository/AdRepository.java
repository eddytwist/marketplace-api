package com.edik.car.api.repository;

import com.edik.car.api.dao.model.Ad;
import com.edik.car.api.repository.projection.AdExtended;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad, Long> {

    @EntityGraph(attributePaths = "pictures")
    Optional<Ad> findOneWithPicturesByAdId(long id);

    Optional<List<AdExtended>> findAllWithPicturesAndUserInformation(Pageable pageable);

//    @Query("select new com.edik.car.api.dao.dto.AdShortInformationService(" +
//        " ad.adId," +
//        " ad.year," +
//        " ad.brand," +
//        " ad.model," +
//        " ad.condition," +
//        " ad.mileage," +
//        " ad.creationTime," +
//        " ui.name," +
//        " ad.pictures.size" +
//        ")" +
//        " from Ad ad" +
//        " left join UserInformation ui on ad.user.userId = ui.userId" +
//        " order by ad.creationTime desc")
//    Optional<List<Ad>> findAllWithPictures(Pageable pageable);
}
