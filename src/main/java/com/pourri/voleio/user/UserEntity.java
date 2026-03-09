package com.pourri.voleio.user;


import com.pourri.voleio.rental.RentalEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users",uniqueConstraints ={@UniqueConstraint(columnNames = {"cpf", "phone"})} )
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String cpf;
    private String password;
    private String phone;
    private String role;
    private Date createdAt;
    private Date updatedAt;
    @OneToMany(mappedBy = "user")
    private List<RentalEntity> rentals;

    public UserEntity() {
    }

    public UserEntity(String email, String username, String cpf, String password, Date createdAt, String role, String phone, Date updatedAt, List<RentalEntity> rentals) {
        this.email = email;
        this.username = username;
        this.cpf = cpf;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
        this.phone = phone;
        this.updatedAt = updatedAt;
        this.rentals = rentals;
    }
}

