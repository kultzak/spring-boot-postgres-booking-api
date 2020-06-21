package com.example.bookingapi.model.repository;

import com.example.bookingapi.model.checkin.CheckInEntity;

import java.util.List;

public interface CheckinRepositoryCustom {

    List<CheckInEntity> getCustomersCheckin(boolean isPastHosted, Long customerId);

}
