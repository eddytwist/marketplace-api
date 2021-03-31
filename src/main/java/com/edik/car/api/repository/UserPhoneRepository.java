package com.edik.car.api.repository;

import com.edik.car.api.dao.model.UserPhone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPhoneRepository extends JpaRepository<UserPhone, Long> {
}
