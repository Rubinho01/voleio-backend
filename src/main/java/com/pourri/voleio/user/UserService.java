package com.pourri.voleio.user;


import com.pourri.voleio.jwt.JwtTokenService;
import com.pourri.voleio.jwt.RecoveryJwtTokenDto;
import com.pourri.voleio.role.Role;
import com.pourri.voleio.role.RoleName;
import com.pourri.voleio.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityConfiguration securityConfiguration;


    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDTO createUserDTO) {

        LocalDateTime now = LocalDateTime.now();
        UserEntity newUser = UserEntity.builder()
                .username(createUserDTO.username())
                .email(createUserDTO.email())
                .cpf(createUserDTO.cpf())
                .password(securityConfiguration.passwordEncoder().encode(createUserDTO.password()))
                .phone(createUserDTO.phone())
                .roles(List.of(Role.builder().name(RoleName.valueOf("ROLE_CUSTOMER")).build()))
                .createdAt(now)
                .updatedAt(now).build();

        userRepository.save(newUser);
    }

    public void createAdmin(CreateUserDTO createUserDTO) {

        LocalDateTime now = LocalDateTime.now();
        UserEntity newAdministrator = UserEntity.builder()
                .username(createUserDTO.username())
                .email(createUserDTO.email())
                .cpf(createUserDTO.cpf())
                .password(securityConfiguration.passwordEncoder().encode(createUserDTO.password()))
                .phone(createUserDTO.phone())
                .roles(List.of(Role.builder().name(RoleName.valueOf("ROLE_ADMINISTRATOR")).build()))
                .createdAt(now)
                .updatedAt(now).build();

        userRepository.save(newAdministrator);
    }

}
