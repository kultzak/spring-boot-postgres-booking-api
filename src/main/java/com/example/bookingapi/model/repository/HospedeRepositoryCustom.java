package com.example.bookingapi.model.repository;


import com.example.bookingapi.model.common.PageResult;

public interface HospedeRepositoryCustom {

    PageResult findHospedesWithCheckin(boolean isPastHosted, Long page, Long size);

}
