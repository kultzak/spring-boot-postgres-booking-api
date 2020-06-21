package com.example.bookingapi.model.repository;

import com.example.bookingapi.model.checkin.CheckInEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CheckinRepository extends JpaRepository<CheckInEntity, String>, CheckinRepositoryCustom {

    Page<CheckInEntity> findAll(Pageable pageable);

}
