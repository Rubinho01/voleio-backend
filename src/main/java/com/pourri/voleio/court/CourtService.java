package com.pourri.voleio.court;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
}
