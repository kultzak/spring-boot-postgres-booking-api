package com.example.bookingapi.controller.checkin.contracts;

import java.time.LocalDateTime;

public class CheckInDTO {


    public String id;

    public Long hospedeId;

    public LocalDateTime chechInDate;

    public LocalDateTime checkOutDate;

    public boolean additional;
}
