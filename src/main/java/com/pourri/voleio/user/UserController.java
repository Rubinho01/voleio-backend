package com.pourri.voleio.user;


import com.pourri.voleio.jwt.RecoveryJwtTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO > authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        List<String> roles = userService.getRoleByEmail(loginUserDto.email())
                .stream()
                .map(role -> role.getName().name())
                .toList();
        Long userId = userService.getIdByEmail(loginUserDto.email());
        LoginResponseDTO response = new LoginResponseDTO(token.token(), roles, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody CreateUserDTO createUserDTO){
        userService.createUser(createUserDTO);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<Void> registerAdministrator(@RequestBody CreateUserDTO createUserDTO){
        userService.createAdmin(createUserDTO);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }


}
