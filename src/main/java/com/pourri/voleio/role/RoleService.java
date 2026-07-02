package com.pourri.voleio.role;


import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {}

    public Optional<Role> getRoleIdByUserRole(Long roleId) {
        return roleRepository.findById(roleId);
    }
}
