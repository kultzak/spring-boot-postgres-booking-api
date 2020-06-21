package com.example.bookingapi.controller.checkin;


import com.example.bookingapi.controller.checkin.contracts.CheckInDTO;
import com.example.bookingapi.controller.checkin.contracts.CheckInOutput;
import com.example.bookingapi.controller.checkin.contracts.HostedCustomersOutut;
import com.example.bookingapi.controller.common.Response;
import com.example.bookingapi.controller.util.Spring;
import com.example.bookingapi.controller.util.APIPaths;
import com.example.bookingapi.model.checkin.CheckInEntity;
import com.example.bookingapi.model.repository.CheckinRepository;
import com.example.bookingapi.model.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(APIPaths.CHECK_IN)
public class CheckinController {

    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private HospedeRepository hospedeRepository;

    @GetMapping(value = "/")
    public Page<CheckInEntity> getCheckin(Pageable pageable) {
        return checkinRepository.findAll(pageable);
    }

    @PostMapping(value = "/register")
    public CheckInEntity createCheckin(@Valid @RequestBody CheckInEntity checkin) {
        return checkinRepository.save(checkin);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Response<CheckInOutput>> createClient(HttpServletRequest request, @RequestBody CheckInDTO checkin, BindingResult result) {
        Response<CheckInOutput> response = new Response<CheckInOutput>();
        try {
            CheckInOutput createdCustumer = Spring.bean(CheckinService.class).createOrUpdateCheckIn(checkin);
            response.setData(createdCustumer);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/past-hosted-hospedes/{page}/{size}")
    public ResponseEntity<Response<HostedCustomersOutut>> pastHostedCustomers(@PathVariable("page") Long page, @PathVariable("size") Long size) {
        Response<HostedCustomersOutut> response = new Response<HostedCustomersOutut>();
        try {
            HostedCustomersOutut pastHosted = Spring.bean(CheckinService.class).getHostedCustomers(true, page, size);
            response.setData(pastHosted);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/hosted-hospedes/{page}/{size}")
    public ResponseEntity<Response<HostedCustomersOutut>> getostedCustomers(@PathVariable("page") Long page, @PathVariable("size") Long size) {
        Response<HostedCustomersOutut> response = new Response<HostedCustomersOutut>();
        try {
            HostedCustomersOutut pastHosted = Spring.bean(CheckinService.class).getHostedCustomers(false, page, size);
            response.setData(pastHosted);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}

