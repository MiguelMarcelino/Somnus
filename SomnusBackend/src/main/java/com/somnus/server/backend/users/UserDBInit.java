package com.somnus.server.backend.users;

import com.somnus.server.backend.users.domain.Role;
import com.somnus.server.backend.users.domain.User;
import com.somnus.server.backend.users.repository.RoleRepository;
import com.somnus.server.backend.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserDBInit {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolesHandler rolesHandler;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @PostConstruct
    public void init() {
        // TODO: Save in Firebase
        if (userRepository.count() == 0) {
            User admin = new User("somnus-admin", adminEmail, "somnus", "admin",
                    rolesHandler.getRole(Role.ADMIN));
            userRepository.save(admin);
        }
    }


}
