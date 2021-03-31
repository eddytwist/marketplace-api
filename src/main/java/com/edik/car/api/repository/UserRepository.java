package com.edik.car.api.repository;

import com.edik.car.api.dao.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"userInformation", "userPhones"})
    Optional<User> findOneWithUserInfoAndUserPhonesByUserId(long id);
}
