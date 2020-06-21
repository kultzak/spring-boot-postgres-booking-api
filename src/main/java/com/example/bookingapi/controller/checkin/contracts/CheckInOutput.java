package com.example.bookingapi.controller.checkin.contracts;

import com.example.bookingapi.controller.hospede.contracts.HospedeDTO;

import java.time.LocalDateTime;

public class CheckInOutput {

    public HospedeDTO hospede;

    public LocalDateTime inDate;

    public LocalDateTime outDate;

    public boolean additional;
}
