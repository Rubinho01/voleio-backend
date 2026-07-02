package com.pourri.voleio.reservation;

import com.pourri.voleio.court.CourtEntity;
import com.pourri.voleio.court.CourtService;
import com.pourri.voleio.security.AuthService;
import com.pourri.voleio.user.UserEntity;
import com.pourri.voleio.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CourtService courtService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    public List<ReservationEntity> dayCourtReservations(LocalDate date, Long courtId) {
        return reservationRepository.findByCourtIdAndDate(courtId, date);
    }

    public List<LocalTime> dayCourtReservationDates(LocalDate date, Long courtId) {
        List<ReservationEntity> dayCourtReservations = dayCourtReservations(date, courtId);
        return dayCourtReservations.stream()
                .map(ReservationEntity::getStartTime).toList();
    }

    public List<LocalTime> availibleDayCourtReservations(LocalDate date, Long courtId) {
        List<LocalTime> allCourtDates = courtService.splitOpenTimeInReferences(courtId);
        List<LocalTime> dayCourtReservations = dayCourtReservationDates(date, courtId);
        return allCourtDates.stream().filter(dateOnCourtDates -> !dayCourtReservations.contains(dateOnCourtDates)).toList();
    }

    @Transactional
    public void newReservation(NewReservationDTO newReservationDto) {
        //Variáveis Locais
        LocalDate today = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        Long userId = authService.getCurrentUserId();
        UserEntity user = userService.getUserEntity(userId);
        CourtEntity courtInfos = courtService.getCourtEntity(newReservationDto.courtId());
        //Tratamento de datas
        if (newReservationDto.reservationDate() == null) {
            throw new IllegalArgumentException("Data inválida");
        } else if (newReservationDto.reservationDate().isBefore(today)) {
            throw new IllegalArgumentException("A data da reserva deve ser igual ou superior à data de hoje");
        }

        //Tratamento de horários
        if (newReservationDto.startTime() == null) {
            throw new IllegalArgumentException("O horário da reserva não pode ser nulo");
        } else if (newReservationDto.reservationDate().equals(today) && newReservationDto.startTime().isBefore(todayTime)) {
            throw new IllegalArgumentException("O horário da reserva deve ser maior que o horário atual para reservas no dia de hoje");
        }

        List<LocalTime> availableDayCourtReservations = availibleDayCourtReservations(newReservationDto.reservationDate(), newReservationDto.courtId());
        if (availableDayCourtReservations.isEmpty()) {
            throw new IllegalArgumentException("Nenhum Horário disponível para essa quadra");
        }
        if (availableDayCourtReservations.contains(newReservationDto.startTime())) {
            ReservationEntity newReservation = ReservationEntity.builder()
                    .startTime(newReservationDto.startTime())
                    .court(courtInfos)
                    .user(user)
                    .date(newReservationDto.reservationDate())
                    .endTime(newReservationDto.startTime()
                            .plusMinutes(courtInfos.getTimeReference()))
                    .build();
            reservationRepository.save(newReservation);

        } else {
            throw new IllegalArgumentException("Horário indisponível");
        }
    }

    public UserReservationsDTO getUserreservations() {
        Long userId = authService.getCurrentUserId();
        LocalDate today = LocalDate.now();

        List<ReservationEntity> all = reservationRepository.findByUserId(userId);

        Map<Boolean, List<ReservationDTO>> partitioned = all.stream()
                .collect(Collectors.partitioningBy(
                        r -> r.getDate().isBefore(today),
                        Collectors.mapping(this::toReservationDTO, Collectors.toList())
                ));

        List<ReservationDTO> past     = partitioned.get(true);
        List<ReservationDTO> upcoming = partitioned.get(false);

        past.sort(Comparator.comparing(ReservationDTO::date).reversed()
                .thenComparing(ReservationDTO::startTime));
        upcoming.sort(Comparator.comparing(ReservationDTO::date)
                .thenComparing(ReservationDTO::startTime));

        return new UserReservationsDTO(past, upcoming);
    }

    private ReservationDTO toReservationDTO(ReservationEntity r) {
        return new ReservationDTO(
                r.getStartTime(),
                r.getEndTime(),
                r.getDate(),
                r.getCourt().getReference()
        );
    }


}
