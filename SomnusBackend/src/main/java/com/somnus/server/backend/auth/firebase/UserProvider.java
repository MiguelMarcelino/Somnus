package com.somnus.server.backend.auth.firebase;

import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.users.UserService;
import com.somnus.server.backend.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProvider {

    @Autowired
    private UserService userService;

    User getUser(String username) {
        User user = this.userService.loadUserByUsername(username);
        if(user == null) {
            throw new SomnusException(ErrorMessage.NO_USER_FOUND);
        }
        return user;
    }
}
