package com.somnus.server.backend.users;

import com.somnus.server.backend.auth.SecurityConstants;
import com.somnus.server.backend.auth.dto.AuthDto;
import com.somnus.server.backend.users.dto.LoginUserDto;
import com.somnus.server.backend.users.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user-api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/authentication/authenticate-user")
    public UserDto authenticateUser(@RequestHeader(value = SecurityConstants.FIREBASE_HEADER_NAME) String firebaseToken){
        System.out.println("ola");
        return userService.authenticateUser(firebaseToken);
    }

}
