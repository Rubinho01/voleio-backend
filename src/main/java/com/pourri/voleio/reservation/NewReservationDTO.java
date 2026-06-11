package com.pourri.voleio.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record NewReservationDTO(
        LocalDate reservationDate,
        Long courtId,
        LocalTime startTime
) {
}
