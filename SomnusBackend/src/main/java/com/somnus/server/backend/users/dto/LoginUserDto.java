package com.somnus.server.backend.users.dto;

import java.io.Serializable;

public class LoginUserDto implements Serializable {

    private String username;
    private String email;
    private String password;

    private LoginUserDto() {}

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
