package com.example.bookingapi.controller.hospede;


import com.example.bookingapi.controller.hospede.contracts.HospedeDTO;
import com.example.bookingapi.model.hospede.HospedeEntity;
import org.springframework.stereotype.Component;

@Component
public class HospedeMapper {

    public HospedeDTO customerDTO(HospedeEntity entity) {
        if (entity == null) {
            return null;
        }

        HospedeDTO dto = new HospedeDTO();
        dto.name = entity.getName();
        dto.phone = entity.getPhone();
        dto.registerId = entity.getDocument();
        dto.creationDate = entity.getCreatedAt();
        dto.id = entity.getId();
        return dto;
    }
}
