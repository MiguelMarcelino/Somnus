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
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (user == null)
            throw new SomnusException(ErrorMessage.NO_USER_FOUND);

        List<RoleEntity> grantedAuthorities = new ArrayList<>();
        for (GrantedAuthority role : user.getAuthorities()) {
            grantedAuthorities.add(new RoleEntity(role.getAuthority()));
        }

        user.setAuthorities(grantedAuthorities);
        return user;
    }

    public UserDto authenticateUser(String firebaseToken, UserDto userDto) {
        if (StringUtils.isBlank(firebaseToken)) {
            throw new IllegalArgumentException("Blank Firebase Token");
        }
        FirebaseTokenHolder firebaseTokenHolder = FirebaseParser.parseToken(firebaseToken);

        // add user to repository if it does not exist already
        User user = userRepository.findByUserName(firebaseTokenHolder.getUid());
        if (user == null) {
            Pair<String, String> firstAndLastName= parseFirstAndLastName(userDto);
            String firstName = firstAndLastName.getFirst();
            String lastName = firstAndLastName.getSecond();
            user = new User(firebaseTokenHolder.getUid(), firebaseTokenHolder.getEmail(),
                    userDto.getDisplayName(), firstName, lastName, rolesHandler.getRole(Role.USER),
                    Role.USER);
            userRepository.save(user);
        }

        // get user roles
        // List<String> roles = new ArrayList<>();
        // user.getAuthorities().stream().forEach(auth -> roles.add(auth.getAuthority()));

        // create new UserDto
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getDisplayName(),
                user.getFirstName(), user.getLastName(), user.getRole().name);
    }

    /**
     * Changes an already existent user and returns the user with the new information
     *
     * @param user    - user trying to modify the saved user data
     * @param userDto - parameters to change
     * @return - new user information
     */
    public UserDto changeUser(User user, UserDto userDto) {
        if (!user.getRole().equals(Role.ADMIN) ||
                !user.getUsername().equals(userDto)) {
            throw new SomnusException(ErrorMessage.ROLE_NOT_ALLOWED);
        }

        User userToModify = this.userRepository.findByUserName(userDto.getUsername());
        if (userToModify == null) {
            throw new SomnusException(ErrorMessage.NO_USER_FOUND);
        }

        userToModify.setUsername(userDto.getUsername());
        userToModify.setEmail(userDto.getEmail());
        userToModify.setFirstName(userDto.getFirstName());
        userToModify.setLastName(userDto.getLastName());

        if (!Strings.isBlank(userDto.getRole()) &&
                user.getRole().equals(Role.ADMIN)) {
            Role newUserRole = Role.valueOf(userDto.getRole().replace(" ", "_").toUpperCase());
            userToModify.setRole(newUserRole);
        }

        this.userRepository.save(userToModify);

        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getDisplayName(),
                user.getFirstName(), user.getLastName(), user.getRole().name);
    }

    /**
     * Parses a display name into first and last names
     * @param userDto
     * @return
     */
    private Pair<String, String> parseFirstAndLastName(UserDto userDto) {
        // TODO: improve
        String firstName = "";
        String lastName = "";
        if (StringUtils.isBlank(userDto.getFirstName()) ||
                StringUtils.isBlank(userDto.getLastName())) {
            String[] name = userDto.getDisplayName().split(" ");
            firstName = name[0];
            for(int i = 1; i < name.length; i++) {
                if(i == name.length - 1) {
                    lastName = lastName.concat(name[i]);
                } else {
                    lastName = lastName.concat(name[i] + " ");
                }
            }
//            lastName = Arrays.stream(name).collect(Collectors.joining(" "));
        }
        return Pair.of(firstName, lastName);
    }
}
