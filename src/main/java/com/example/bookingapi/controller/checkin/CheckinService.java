package com.example.bookingapi.controller.checkin;


import com.example.bookingapi.controller.checkin.contracts.CheckInDTO;
import com.example.bookingapi.controller.checkin.contracts.CheckInOutput;
import com.example.bookingapi.controller.checkin.contracts.HostedCustomersOutut;
import com.example.bookingapi.controller.checkin.contracts.PastHostedCustomersDTO;
import com.example.bookingapi.controller.util.Spring;
import com.example.bookingapi.controller.util.exception.ResourceNotFoundException;
import com.example.bookingapi.model.checkin.CheckInEntity;
import com.example.bookingapi.model.common.PageResult;
import com.example.bookingapi.model.hospede.HospedeEntity;
import com.example.bookingapi.model.repository.CheckinRepository;
import com.example.bookingapi.model.repository.HospedeRepository;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CheckinService {

    public CheckInOutput createOrUpdateCheckIn(CheckInDTO checkinDTO) {
        CheckInEntity checkin = new CheckInEntity();

        if (checkinDTO.id != null) {
            Optional<CheckInEntity> c = Spring.bean(CheckinRepository.class).findById(checkinDTO.id);
            checkin = c.isPresent() ? c.get() : checkin;
        }

        Optional<HospedeEntity> hospede = Spring.bean(HospedeRepository.class).findCustomerById(checkinDTO.hospedeId);
        if (hospede.isPresent()) {
            checkin.setHospede(hospede.isPresent() ? hospede.get() : null);
        } else {
            new ResourceNotFoundException("Hospede com id: " + checkinDTO.hospedeId + " não encontrado.");
        }

        checkin.setAdditional(checkinDTO.additional);
        checkin.setCheckInDate(checkinDTO.chechInDate);
        checkin.setCheckOutDate(checkinDTO.checkOutDate);
        Spring.bean(CheckinRepository.class).save(checkin);
        return Spring.bean(CheckinMapper.class).toCheckinDTO(checkin);
    }

    public HostedCustomersOutut getHostedCustomers(boolean isPastHosted, Long page, Long size) {
        HostedCustomersOutut outut = new HostedCustomersOutut();
        outut.pastHosted = new ArrayList<>();
        PageResult<HospedeEntity> allCustomers = Spring.bean(HospedeRepository.class).findHospedesWithCheckin(isPastHosted, page, size);
        outut.registeredFound = allCustomers.getTotalCount();
        allCustomers.getRows().forEach(hospede -> {
            List<CheckInEntity> hospedeCheckins = Spring.bean(CheckinRepository.class).getCustomersCheckin(isPastHosted, hospede.getId());
            if (!hospedeCheckins.isEmpty()) {
                PastHostedCustomersDTO dto = new PastHostedCustomersDTO();
                dto.hospedeId = hospede.getId();
                dto.custmerName = hospede.getName();
                dto.lastHostedValue = format(getHostedValue(hospedeCheckins.stream().findFirst().get()));
                dto.allhostedValue = getAllCheckinHostedValue(hospedeCheckins);
                dto.registerId = hospede.getDocument();
                outut.pastHosted.add(dto);
            }
        });
        return outut;
    }

    private String getAllCheckinHostedValue(List<CheckInEntity> hospedeCheckins) {
        AtomicReference<Double> hostedAmount = new AtomicReference<Double>(new Double(0));
        hospedeCheckins.forEach(checkIn -> hostedAmount.set(hostedAmount.get() + getHostedValue(checkIn)));
        return format(hostedAmount.get());
    }


    private Double getHostedValue(CheckInEntity checkin) {
        LocalDateTime startDate = checkin.getChechInDate();
        LocalDateTime endDate = checkin.getCheckOutDate();
        List<LocalDateTime> hostedDays = Stream.iterate(startDate, start -> start.plusDays(1)).limit(ChronoUnit.DAYS.between(startDate, endDate)).collect(Collectors.toList());
        Double weekenddaysValue = getWeekEndDaysValue(hostedDays, endDate, checkin.getAdditional());
        Double weekDaysValue = getWeekDaysValue(hostedDays, checkin.getAdditional());

        return weekenddaysValue + weekDaysValue;
    }

    private Double getWeekEndDaysValue(List<LocalDateTime> hostedDays, LocalDateTime endDate, boolean hasAddicional) {
        Long weekenddays = hostedDays.stream().filter(data -> data.getDayOfWeek().equals(DayOfWeek.SATURDAY) && data.getDayOfWeek().equals(DayOfWeek.SUNDAY)).count();
        boolean lasDayWasWeekEnd = endDate.toLocalTime().isAfter(LocalTime.parse("16:30:00"));
        weekenddays = lasDayWasWeekEnd ? weekenddays + 1L : weekenddays;
        Double weekenddaysValue = new Double(weekenddays * (hasAddicional ? 150 + 20 : 150));
        return weekenddaysValue;
    }

    private Double getWeekDaysValue(List<LocalDateTime> hostedDays, boolean hasAddicional) {
        Long weekdays = hostedDays.stream().filter(data -> !data.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !data.getDayOfWeek().equals(DayOfWeek.SUNDAY)).count();
        Double weekDaysValue = new Double(weekdays * (hasAddicional ? 120 + 15 : 120));
        return weekDaysValue;
    }

    private String format(Double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }
}