package com.somnus.server.bakend.authservice.users;

import com.somnus.server.bakend.authservice.users.domain.Role;
import com.somnus.server.bakend.authservice.users.domain.RoleEntity;
import com.somnus.server.bakend.authservice.users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RolesHandler {

    @Autowired
    private RoleRepository roleRepository;

    public RolesHandler(){}

    public List<RoleEntity> getRole(Role role) {
        return Collections.singletonList(getRole(role.name));
    }

    /**
     * Get or create role
     * @param authority
     * @return
     */
    private RoleEntity getRole(String authority) {
        RoleEntity adminRole = roleRepository.findByAuthority(authority);
        if (adminRole == null) {
            return new RoleEntity(authority);
        } else {
            return adminRole;
        }
    }
}
