package com.pourri.voleio.court;


import com.pourri.voleio.rental.RentalEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courts")
@Getter
@Setter
public class CourtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String description;
    private Integer timeReference;
    private BigDecimal priceOfReference;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "court")
    private List<RentalEntity> rentals;

    public CourtEntity() {
    }

    public CourtEntity(String reference, String description, Integer timeReference, BigDecimal priceOfReference, LocalTime startTime, Boolean isActive, LocalTime endTime, Date createdAt, Date updatedAt, List<RentalEntity> rentals) {
        this.reference = reference;
        this.description = description;
        this.timeReference = timeReference;
        this.priceOfReference = priceOfReference;
        this.startTime = startTime;
        this.isActive = isActive;
        this.endTime = endTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.rentals = rentals;
    }
}
