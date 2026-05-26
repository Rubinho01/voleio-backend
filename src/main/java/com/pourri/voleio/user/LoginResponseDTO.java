package com.pourri.voleio.user;

import java.util.List;

public record LoginResponseDTO(String token, List<String> roles, Long id) {

}
