package com.pourri.voleio.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    ReservationEntity findByCourtId(Long courtId);
    List<ReservationEntity> findByCourtIdAndDate(Long courtId, LocalDate date);



}
