package com.somnus.server.backend.users;

import com.somnus.server.backend.auth.firebase.FirebaseParser;
import com.somnus.server.backend.auth.firebase.FirebaseTokenHolder;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.users.domain.Role;
import com.somnus.server.backend.users.domain.User;
import com.somnus.server.backend.users.dto.UserDto;
import com.somnus.server.backend.users.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesHandler rolesHandler;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null)
            throw new SomnusException(ErrorMessage.NO_USER_FOUND);

//        List<RoleEntity> grantedAuthorities = new ArrayList<>();
//        for (GrantedAuthority role : user.getAuthorities()) {
//            grantedAuthorities.add(new RoleEntity(role.getAuthority()));
//        }
//        user.setAuthorities(grantedAuthorities);

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
                    userDto.getDisplayName(), firstName, lastName, Role.USER, userDto.getPhotoURL());
            userRepository.save(user);
        }

        // get user roles
        // List<String> roles = new ArrayList<>();
        // user.getAuthorities().stream().forEach(auth -> roles.add(auth.getAuthority()));

        // create new UserDto
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getDisplayName(),
                user.getFirstName(), user.getLastName(), user.getRole().name, user.getPhotoURL());
    }

    /**
     * Changes an already existent user and returns the user with the new information
     *
     * @param user    - user trying to modify the saved user data
     * @param userDto - parameters to change
     * @return - new user information
     */
    public UserDto changeUser(User user, UserDto userDto) {
        if (!user.getRole().equals(Role.ADMIN) &&
                !user.getUsername().equals(userDto.getUserID())) {
            throw new SomnusException(ErrorMessage.ROLE_NOT_ALLOWED);
        }

        User userToModify = this.userRepository.findByUserName(userDto.getUserID());
        if (userToModify == null) {
            throw new SomnusException(ErrorMessage.NO_USER_FOUND);
        }

        String firstName = userToModify.getFirstName();
        String lastName = userToModify.getLastName();
        String email = userToModify.getEmail();
        Role newUserRole = userToModify.getRole();
        String displayName;

        if(!Strings.isBlank(userDto.getEmail())) {
            userToModify.setEmail(userDto.getEmail());
        }
        if(!Strings.isBlank(userDto.getFirstName())) {
            firstName = userDto.getFirstName();
            userToModify.setFirstName(userDto.getFirstName());
        }
        if(!Strings.isBlank(userDto.getLastName())) {
            lastName = userDto.getLastName();
            userToModify.setLastName(userDto.getLastName());
        }

        userToModify.setDisplayName(firstName + " "
                + lastName);
        displayName = firstName + " " + lastName;

        if (!Strings.isBlank(userDto.getRole()) &&
                user.getRole().equals(Role.ADMIN)) {
            newUserRole = Role.valueOf(userDto.getRole().replace(" ", "_").toUpperCase());
            userToModify.setRole(newUserRole);
        }

        this.userRepository.save(userToModify);

        return new UserDto(userToModify.getId(), userToModify.getUsername(), email, displayName,
                firstName, lastName, newUserRole.name, user.getPhotoURL());
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
