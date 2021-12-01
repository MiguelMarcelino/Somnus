package com.somnus.server.bakend.authservice.users;

import com.somnus.server.bakend.authservice.users.domain.Role;
import com.somnus.server.bakend.authservice.users.domain.User;
import com.somnus.server.bakend.authservice.users.repository.RoleRepository;
import com.somnus.server.bakend.authservice.users.repository.UserRepository;
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
            // username from firebase
            User admin = new User("wdhAh6fNpPQ6CDVeal7X8a6LnuP2", adminEmail, "Somnus Admin",
                    "somnus", "admin", Role.ADMIN, null);
            userRepository.save(admin);
        }
    }


}