package com.somnus.server.backend.users.dto;

import com.somnus.server.backend.users.domain.Role;

import java.io.Serializable;

public class UserDto implements Serializable {

    private String uid;
    private String username;
    private String email;
    private Role role;

    public UserDto(String uid, String username, String email, Role role) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
