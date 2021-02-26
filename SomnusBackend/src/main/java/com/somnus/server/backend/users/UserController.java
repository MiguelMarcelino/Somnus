package com.somnus.server.backend.users;

import com.somnus.server.backend.users.domain.User;
import com.somnus.server.backend.users.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user-api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/authentication/authenticate-user")
    public UserDto authenticateUser(@RequestHeader String firebaseToken, @RequestBody UserDto userDto){
        return userService.authenticateUser(firebaseToken, userDto);
    }

    @PostMapping(value = "/update-user")
    public UserDto updateUser(Principal principal, @RequestBody UserDto userDto) {
        User user = (User) ((Authentication) principal).getPrincipal();

        return userService.changeUser(user, userDto);
    }
}
