package com.edik.car.api.repository;

import com.edik.car.api.dao.model.Picture;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    @EntityGraph(attributePaths = "ad")
    Optional<Picture> findOneWithAdByPictureId(long id);
}
