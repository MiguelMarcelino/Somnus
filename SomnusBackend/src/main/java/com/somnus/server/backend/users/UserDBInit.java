package com.somnus.server.backend.users;

import com.somnus.server.backend.users.domain.Role;
import com.somnus.server.backend.users.domain.RoleEntity;
import com.somnus.server.backend.users.domain.User;
import com.somnus.server.backend.users.repository.RoleRepository;
import com.somnus.server.backend.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Service
public class UserDBInit {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @PostConstruct
    public void init() {
        // Add admin to user DB
        if (userRepository.count() == 0) {
            User admin = new User("somnus-admin", "882e4de25d20", adminEmail, getAdminRoles());
            userRepository.save(admin);
        }
    }

    private List<RoleEntity> getAdminRoles() {
        return Collections.singletonList(getRole(Role.ADMIN.name));
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
