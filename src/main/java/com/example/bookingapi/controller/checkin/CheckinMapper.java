package com.example.bookingapi.controller.checkin;


import com.example.bookingapi.controller.checkin.contracts.CheckInOutput;
import com.example.bookingapi.controller.checkin.contracts.PastHostedCustomersDTO;
import com.example.bookingapi.controller.util.Spring;
import com.example.bookingapi.controller.hospede.HospedeMapper;
import com.example.bookingapi.model.checkin.CheckInEntity;
import org.springframework.stereotype.Component;

@Component
public class CheckinMapper {

    public CheckInOutput toCheckinDTO(CheckInEntity entity) {
        if (entity == null) {
            return null;
        }

        CheckInOutput dto = new CheckInOutput();
        dto.additional= entity.getAdditional();
        dto.inDate = entity.getChechInDate();
        dto.outDate = entity.getCheckOutDate();
        dto.hospede = Spring.bean(HospedeMapper.class).customerDTO(entity.getHospede());
        return dto;
    }

    public PastHostedCustomersDTO toPastHostedCustomersDTO(CheckInEntity entity) {
        if (entity == null) {
            return null;
        }

        PastHostedCustomersDTO dto = new PastHostedCustomersDTO();
        dto.custmerName = entity.getHospede().getName();

        return dto;
    }
}
