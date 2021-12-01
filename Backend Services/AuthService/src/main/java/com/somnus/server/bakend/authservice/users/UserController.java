package com.somnus.server.bakend.authservice.users;

import com.somnus.server.bakend.authservice.users.domain.User;
import com.somnus.server.bakend.authservice.users.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public UserDto updateUser(@RequestBody User user, @RequestBody UserDto userDto) {
        return userService.changeUser(user, userDto);
    }
}
