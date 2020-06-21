package com.example.bookingapi.model.repository;

import com.example.bookingapi.model.common.PageResult;
import com.example.bookingapi.model.hospede.HospedeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospedeRepository extends JpaRepository<HospedeEntity, Long>,
        JpaSpecificationExecutor<HospedeEntity> {

    PageResult<HospedeEntity> findHospedesWithCheckin(boolean isPastHosted, Long page, Long size);

    Optional<HospedeEntity> findCustomerById(Long customerId);
}
