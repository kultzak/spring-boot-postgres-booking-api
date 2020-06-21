package com.example.bookingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HotelBookingAPI {
	public static void main(String[] args) {
		SpringApplication.run(HotelBookingAPI.class, args);
	}
}
