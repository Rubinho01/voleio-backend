package com.pourri.voleio.court;

import java.math.BigDecimal;
import java.time.LocalTime;

public record ListAllCourtsDTO(
        Long id,
        String reference,
        String description,
        Integer timeReference,
        BigDecimal priceOfReference,
        LocalTime startTime,
        LocalTime endTime
) {
}
