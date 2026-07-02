package com.pourri.voleio.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<CourtEntity, Long> {
    List<CourtEntity> findByReference(String reference);
    List<CourtEntity> findByIsActive(Boolean isActive);
    CourtEntity findById(long id);
}
