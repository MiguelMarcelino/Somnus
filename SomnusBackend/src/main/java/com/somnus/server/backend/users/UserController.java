package com.somnus.server.backend.users;

import com.somnus.server.backend.users.dto.LoginUserDto;
import com.somnus.server.backend.users.dto.NewUserDto;
import com.somnus.server.backend.users.dto.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user-api")
public class UserController {

    @PostMapping
    public UserDto registerNewUser(@RequestBody NewUserDto newUserDto){
        // TODO
        return null;
    }

    public UserDto loginUser(@RequestBody LoginUserDto loginUserDto) {
        // TODO
        return null;
    }

}
