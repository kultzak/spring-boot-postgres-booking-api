package com.example.bookingapi.model.repository;


import com.example.bookingapi.model.checkin.CheckInEntity;
import com.example.bookingapi.model.checkin.QCheckInEntity;
import com.example.bookingapi.model.common.RepositoryBaseImpl;
import com.querydsl.jpa.impl.JPAQuery;

import java.time.LocalDateTime;
import java.util.List;

public class CheckinRepositoryImpl extends RepositoryBaseImpl implements CheckinRepositoryCustom {

    @Override
    public List<CheckInEntity> getCustomersCheckin(boolean isPastHosted, Long customerId) {
        LocalDateTime now = LocalDateTime.now();
        QCheckInEntity checkin = QCheckInEntity.checkInEntity;
        JPAQuery<CheckInEntity> query = select(checkin).from(checkin);
        if (isPastHosted) {
            query.where(checkin.checkOutDate.before(now));
        } else {
            query.where(checkin.checkOutDate.after(now).and(checkin.chechInDate.before(now)));
        }
        query.where(checkin.hospedeId.eq(customerId));
        query.orderBy(checkin.checkOutDate.desc());

        return query.fetch();
    }

}
