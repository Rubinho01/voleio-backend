package com.pourri.voleio.reservation;

import java.util.List;

public record UserReservationsDTO(
        List<ReservationDTO> pastRentals,
        List<ReservationDTO> upcomingRentals
) {}

