package com.pourri.voleio.user;


import com.pourri.voleio.rental.RentalEntity;
import com.pourri.voleio.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users",uniqueConstraints ={@UniqueConstraint(columnNames = {"cpf", "phone"})} )
@AllArgsConstructor
@Builder
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    @Singular
    private List<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "user")
    private List<RentalEntity> rentals;

    public UserEntity() {
    }


}

