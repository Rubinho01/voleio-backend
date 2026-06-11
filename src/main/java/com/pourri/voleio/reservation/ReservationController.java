package com.pourri.voleio.reservation;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;


    @GetMapping("/available") // GET /reservations/available?date=2026-06-10&courtId=1
    public ResponseEntity<?> getAvailableDayCourtReservations(
            @RequestParam LocalDate date,
            @RequestParam Long courtId) {
        try {
            List<LocalTime> availableTimes = reservationService.availibleDayCourtReservations(date, courtId);
            return ResponseEntity.ok(availableTimes);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @PostMapping("/new")
    public ResponseEntity<?> newReservation(@RequestBody NewReservationDTO newReservationDto) {
        try {
            reservationService.newReservation(newReservationDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
