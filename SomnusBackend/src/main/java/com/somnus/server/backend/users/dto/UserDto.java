package com.somnus.server.backend.users.dto;

import com.somnus.server.backend.users.domain.Role;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    private String username;
    private String email;
    private List<String> roles;

    public UserDto(String username, String email, List<String> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRole() {
        return roles;
    }
}
