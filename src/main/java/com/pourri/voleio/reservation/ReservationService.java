package com.pourri.voleio.reservation;

import com.pourri.voleio.court.CourtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private CourtService courtService;

    public List<ReservationEntity> dayCourtReservations(LocalDate date, Long courtId){
        return reservationRepository.findByCourtIdAndDate(courtId,date);
    }

    public List<LocalTime> dayCourtReservationDates(LocalDate date, Long courtId){
        List<ReservationEntity> dayCourtReservations = dayCourtReservations(date, courtId);
        return dayCourtReservations.stream()
                .map(ReservationEntity::getStartTime).toList();

    }

    public List<LocalTime> availibleDayCourtReservations(LocalDate date, Long courtId){
        List<LocalTime> allCourtDates = courtService.splitOpenTimeInReferences(courtId);
        List<LocalTime> dayCourtReservations = dayCourtReservationDates(date, courtId);
        return allCourtDates.stream().filter(dateOnCourtDates -> !dayCourtReservations.contains(dateOnCourtDates)).toList();
    }

    @Transactional
    public void newReservation(LocalDate reservationDate, Long courtId, Long userId, LocalTime startTime){
        LocalDate today = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        //Tratamento de datas
        if (reservationDate == null){ throw  new IllegalArgumentException("Data inválida"); } else if (reservationDate.isBefore(today)) {
            throw  new IllegalArgumentException("A data da reserva deve ser igual ou superior à data de hoje");
        }

        //Tratamento de horários
        if (startTime == null){ throw new IllegalArgumentException("O horário da reserva não pode ser nulo");
        } else if (reservationDate.equals(today) && startTime.isBefore(todayTime)) {
            throw new IllegalArgumentException("O horário da reserva deve ser maior que o horário atual para reservas no dia de hoje");
        }

        List<LocalTime> availibleDayCourtReservations = availibleDayCourtReservations(reservationDate, courtId);
        if (availibleDayCourtReservations.isEmpty()){ throw new  IllegalArgumentException("Nenhum Horário disponível para essa quadra"); }




    }
