package com.pourri.voleio.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDTO(
        LocalTime startTime,
        LocalTime endTime,
        LocalDate date,
        String courtName
) {}