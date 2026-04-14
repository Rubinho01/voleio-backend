package com.pourri.voleio.user;

public record CreateUserDTO(
        String username,
        String email,
        String cpf,
        String password,
        String phone
) {
}
