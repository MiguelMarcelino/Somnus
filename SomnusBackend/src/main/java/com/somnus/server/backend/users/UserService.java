package com.somnus.server.backend.users;

import com.somnus.server.backend.auth.firebase.FirebaseParser;
import com.somnus.server.backend.auth.firebase.FirebaseTokenHolder;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.users.domain.Role;
import com.somnus.server.backend.users.domain.RoleEntity;
import com.somnus.server.backend.users.domain.User;
import com.somnus.server.backend.users.dto.UserDto;
import com.somnus.server.backend.users.repository.RoleRepository;
import com.somnus.server.backend.users.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolesHandler rolesHandler;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null)
            throw new SomnusException(ErrorMessage.NO_USER_FOUND);

        List<RoleEntity> grantedAuthorities = new ArrayList<>();
        for (GrantedAuthority role : user.getAuthorities()) {
            grantedAuthorities.add(new RoleEntity(role.getAuthority()));
        }

        user.setAuthorities(grantedAuthorities);
        return user;
    }

    public UserDto authenticateUser(String firebaseToken) {
        if(StringUtils.isBlank(firebaseToken)) {
            throw new IllegalArgumentException("Blank Firebase Token");
        }
        FirebaseTokenHolder firebaseTokenHolder = FirebaseParser.parseToken(firebaseToken);

        // add user to repository if it does not exist already
        User user = userRepository.findByUserName(firebaseTokenHolder.getUid());
        if(user == null) {
            user = new User(firebaseTokenHolder.getUid(),
                    firebaseTokenHolder.getEmail(), rolesHandler.getRole(Role.USER));
            userRepository.save(user);
        }

        // get user roles
        List<String> roles = new ArrayList<>();
        user.getAuthorities().stream().forEach(auth -> roles.add(auth.getAuthority()));

        // create new UserDto
        return new UserDto(user.getUsername(), user.getEmail(), roles);
    }

    public UserDto registerUser(String firebaseToken, UserDto userDto) {
        if(StringUtils.isBlank(firebaseToken)) {
            throw new IllegalArgumentException("Blank Firebase Token");
        }
        FirebaseTokenHolder firebaseTokenHolder = FirebaseParser.parseToken(firebaseToken);

        // add user to repository if it does not exist already
        User user = userRepository.findByUserName(firebaseTokenHolder.getUid());
        if(user == null) {
            user = new User(firebaseTokenHolder.getUid(), firebaseTokenHolder.getEmail(),
                    userDto.getFirstName(), userDto.getLastName(), rolesHandler.getRole(Role.USER));
            userRepository.save(user);
        }

        // get user roles
        List<String> roles = new ArrayList<>();
        user.getAuthorities().forEach(auth -> roles.add(auth.getAuthority()));

        // create new UserDto
        return new UserDto(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), roles);
    }
}
