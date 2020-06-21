package com.example.bookingapi.model.checkin;


import com.example.bookingapi.model.hospede.HospedeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hospede_checkin")
public class CheckInEntity {
    @Id
    @GeneratedValue(generator = "checkin_generator")
    @SequenceGenerator(
            name = "checkin_generator",
            sequenceName = "checkin_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(name = "hospede", nullable = false)
    private Long hospedeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospede", referencedColumnName = "ID", insertable = false, updatable = false)
    private HospedeEntity hospede;

    @Column(name = "additional", nullable = false)
    private boolean additional;

    @Column(name = "checkin_date", nullable = false)
    private LocalDateTime chechInDate;

    @Column(name = "checkout_date", nullable = false)
    private LocalDateTime checkOutDate;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHospedeId() {
        return hospedeId;
    }

    public void setHospedeId(Long hospedeId) {
        this.hospedeId = hospedeId;
    }

    public HospedeEntity getHospede() {
        return hospede;
    }

    public void setHospede(HospedeEntity hospede) {
        this.setHospedeId(hospede.getId());
        this.hospede = hospede;
    }


    public void setAdditional(boolean additional) {
        this.additional = additional;
    }

    public boolean getAdditional() {
        return additional;
    }

    public LocalDateTime getChechInDate() {
        return chechInDate;
    }

    public void setCheckInDate(LocalDateTime chechInDate) {
        this.chechInDate = chechInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

}
