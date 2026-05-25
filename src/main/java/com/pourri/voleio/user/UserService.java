package com.pourri.voleio.user;


import com.pourri.voleio.jwt.JwtTokenService;
import com.pourri.voleio.jwt.RecoveryJwtTokenDto;
import com.pourri.voleio.role.Role;
import com.pourri.voleio.role.RoleName;
import com.pourri.voleio.role.RoleRepository;
import com.pourri.voleio.role.RoleService;
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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;


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
        Role customerRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Role CUSTOMER não encontrada no banco"));

        LocalDateTime now = LocalDateTime.now();
        UserEntity newUser = UserEntity.builder()
                .username(createUserDTO.username())
                .email(createUserDTO.email())
                .cpf(createUserDTO.cpf())
                .password(securityConfiguration.passwordEncoder().encode(createUserDTO.password()))
                .phone(createUserDTO.phone())
                .roles(List.of(customerRole))
                .createdAt(now)
                .updatedAt(now).build();

        userRepository.save(newUser);
    }

    public List<Role> getRoleByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return userEntity.getRoles();
    }


    public void createAdmin(CreateUserDTO createUserDTO) {
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMINISTRATOR)
                .orElseThrow(() -> new RuntimeException("Role ADMINISTRATOR não encontrada no banco"));

        LocalDateTime now = LocalDateTime.now();
        UserEntity newAdministrator = UserEntity.builder()
                .username(createUserDTO.username())
                .email(createUserDTO.email())
                .cpf(createUserDTO.cpf())
                .password(securityConfiguration.passwordEncoder().encode(createUserDTO.password()))
                .phone(createUserDTO.phone())
                .roles(List.of(adminRole))
                .createdAt(now)
                .updatedAt(now).build();

        userRepository.save(newAdministrator);
    }

}
