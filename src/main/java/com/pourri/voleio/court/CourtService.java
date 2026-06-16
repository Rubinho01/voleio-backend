package com.pourri.voleio.court;


import jakarta.persistence.EntityNotFoundException;
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

    public CourtEntity getCourtEntity(Long courtId){
        return courtRepository.findById(courtId)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada"));
    }

    public List<LocalTime> splitOpenTimeInReferences(Long courtId) {
        CourtEntity court = courtRepository.findById(courtId)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada."));

        if (court.getStartTime() == null || court.getEndTime() == null) {
            throw new IllegalArgumentException("As datas de início e fim não podem ser nulas.");
        }
        if (court.getStartTime().isAfter(court.getEndTime())) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim.");
        }
        if (court.getTimeReference() <= 0) {
            throw new IllegalArgumentException("O intervalo de minutos deve ser maior que zero.");
        }
        List<LocalTime> result = new ArrayList<>();
        LocalTime current = court.getStartTime();

        while (!current.plusMinutes(court.getTimeReference())
                .isAfter(court.getEndTime())) {
            result.add(current);
            current = current.plusMinutes(court.getTimeReference());
        }

        return result;
    }

    public void updateCourt(Long courtId, CreateCourtDTO dto) {
        CourtEntity court = courtRepository.findById(courtId)
                .orElseThrow(() -> new EntityNotFoundException("Quadra não encontrada"));

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");

        court.setReference(dto.reference());
        court.setDescription(dto.description());
        court.setTimeReference(dto.timeReference());
        court.setPriceOfReference(dto.priceOfReference());
        court.setStartTime(LocalTime.parse(dto.startTime().format(formatador)));
        court.setEndTime(LocalTime.parse(dto.endTime().format(formatador)));
        court.setUpdatedAt(LocalDateTime.now());

        courtRepository.save(court);
    }
}
