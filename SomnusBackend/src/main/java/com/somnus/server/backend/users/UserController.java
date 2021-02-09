package com.somnus.server.backend.users;

import com.somnus.server.backend.users.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user-api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/authentication/authenticate-user")
    public UserDto authenticateUser(@RequestHeader String firebaseToken){
        return userService.authenticateUser(firebaseToken);
    }

    @PostMapping(value = "/authentication/register-user")
    public UserDto registerUser(@RequestHeader String firebaseToken, @RequestBody UserDto userDto){
        return userService.registerUser(firebaseToken, userDto);
    }

}
