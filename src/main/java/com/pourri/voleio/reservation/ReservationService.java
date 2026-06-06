package com.pourri.voleio.reservation;

import com.pourri.voleio.court.CourtService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private CourtService courtService;

    public List<ReservationEntity> dayCourtReservations(Date date, Long courtId){
        return reservationRepository.findByCourtIdAndDate(courtId,date);
    }

    public List<LocalTime> dayCourtReservationDates(Date date, Long courtId){
        List<ReservationEntity> dayCourtReservations = dayCourtReservations(date, courtId);
        return dayCourtReservations.stream()
                .map(ReservationEntity::getStartTime).toList();

    }

    public List<LocalTime> availibleDayCourtReservations(Date date, Long courtId){
        List<LocalTime> allCourtDates = courtService.splitOpenTimeInReferences(courtId);
        List<LocalTime> dayCourtReservations = dayCourtReservationDates(date, courtId);
        return allCourtDates.stream().filter(dateOnCourtDates -> !dayCourtReservations.contains(dateOnCourtDates)).toList();
    }
}
