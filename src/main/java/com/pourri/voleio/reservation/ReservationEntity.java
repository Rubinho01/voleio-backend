package com.pourri.voleio.reservation;


import com.pourri.voleio.court.CourtEntity;
import com.pourri.voleio.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "reservations")
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "court_id")
    private CourtEntity court;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private Date createdAt;
    private Date updatedAt;

    public ReservationEntity() {
    }
}
