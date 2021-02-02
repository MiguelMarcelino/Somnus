package com.somnus.server.backend.users.dto;

import java.io.Serializable;

public class NewUserDto implements Serializable {

    private String uid;
    private String username;
    private String email;
    private String password;

    private NewUserDto() {}

    public String getUid() {
        return uid;
    }

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
