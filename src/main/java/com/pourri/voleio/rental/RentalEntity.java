package com.pourri.voleio.rental;


import com.pourri.voleio.court.CourtEntity;
import com.pourri.voleio.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "rentals")
@Getter
@Setter
@ToString
public class RentalEntity {

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
    private Integer numberOfReferences;
    private Date createdAt;
    private Date updatedAt;

    public RentalEntity(Date updatedAt, Date createdAt, Integer numberOfReferences, LocalTime endTime, LocalTime startTime, CourtEntity court, UserEntity user) {
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.numberOfReferences = numberOfReferences;
        this.endTime = endTime;
        this.startTime = startTime;
        this.court = court;
        this.user = user;
    }

    public RentalEntity() {
    }
}
