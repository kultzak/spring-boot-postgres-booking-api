package com.example.bookingapi.model.repository;

import com.example.bookingapi.model.checkin.QCheckInEntity;
import com.example.bookingapi.model.common.PageRequest;
import com.example.bookingapi.model.common.PageResult;
import com.example.bookingapi.model.common.RepositoryBaseImpl;
import com.example.bookingapi.model.hospede.HospedeEntity;
import com.example.bookingapi.model.hospede.QHospedeEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class HospedeRepositoryImpl extends RepositoryBaseImpl implements HospedeRepositoryCustom {

    @Override
    public PageResult findHospedesWithCheckin(boolean isPastHosted, Long page, Long size) {
        LocalDateTime now = LocalDateTime.now();
        QCheckInEntity checkin = QCheckInEntity.checkInEntity;
        JPAQuery<Long> checkinQuery = select(checkin.hospedeId).from(checkin);
        if (isPastHosted){
            checkinQuery.where(checkin.checkOutDate.before(now));
        } else {
            checkinQuery.where(checkin.checkOutDate.after(now).and(checkin.chechInDate.before(now)));
        }

        QHospedeEntity customer = QHospedeEntity.hospedeEntity;
        JPAQuery<HospedeEntity> query = select(customer).from(customer);
        query.where(customer.id.in(checkinQuery));

        PageRequest pageRequest = new PageRequest(page, size);
        return getPagedQuery(query, pageRequest);
    }
}
