package com.pourri.voleio.user;

import com.pourri.voleio.rental.RentalEntity;
import com.pourri.voleio.role.Role;
import com.pourri.voleio.role.RoleName;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public record CreateUserDTO(
        String username,
        String email,
        String cpf,
        String password,
        String phone,
        RoleName role
) {
}
