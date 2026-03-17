package com.pourri.voleio.user;


import com.pourri.voleio.rental.RentalEntity;
import com.pourri.voleio.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users",uniqueConstraints ={@UniqueConstraint(columnNames = {"cpf", "phone"})} )
@Getter
@Setter
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String cpf;
    private String password;
    private String phone;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;
    private Date createdAt;
    private Date updatedAt;
    @OneToMany(mappedBy = "user")
    private List<RentalEntity> rentals;

    public UserEntity() {
    }


}

