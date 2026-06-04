package com.pourri.voleio.court;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourtService {

    @Autowired
    private CourtRepository courtRepository;

    public void createCourt(CreateCourtDTO CreateCourtDTO){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        CourtEntity newCourt = CourtEntity.builder()
                .reference(CreateCourtDTO.reference())
                .description(CreateCourtDTO.description())
                .timeReference(CreateCourtDTO.timeReference())
                .priceOfReference(CreateCourtDTO.priceOfReference())
                .startTime(LocalTime.parse(CreateCourtDTO.startTime().format(formatador)))
                .endTime(LocalTime.parse(CreateCourtDTO.endTime().format(formatador)))
                .isActive(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        courtRepository.save(newCourt);

    }

    public List<ListAllCourtsDTO> listAllActiveCourts(){
        List<CourtEntity> courts = courtRepository.findByIsActive(true);

                return courts.stream()
                        .map(court -> new ListAllCourtsDTO(
                                court.getId(),
                                court.getReference(),
                                court.getDescription(),
                                court.getTimeReference(),
                                court.getPriceOfReference(),
                                court.getStartTime(),
                                court.getEndTime()
                        ))
                        .toList();
    }

    public List<LocalDateTime> splitOpenTimeInReferences(LocalDateTime start, LocalDateTime end, long reference) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("As datas de início e fim não podem ser nulas.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim.");
        }
        if (reference <= 0) {
            throw new IllegalArgumentException("O intervalo de minutos deve ser maior que zero.");
        }

        List<LocalDateTime> result = new ArrayList<>();
        LocalDateTime current = start;

        while (!current.isAfter(end)) {
            result.add(current);
            current = current.plusMinutes(reference);
        }

        return result;
    }
}
