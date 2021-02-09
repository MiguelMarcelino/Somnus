package com.somnus.server.backend.users.dto;

import com.somnus.server.backend.users.domain.Role;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private List<String> roles;

    private UserDto() {}

    public UserDto(String username, String email, List<String> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public UserDto(String username, String email, String firstName, String lastName, List<String> roles) {
        this.username = username;
        this.email = email;
        this.firstName = username;
        this.lastName = lastName;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getRole() {
        return roles;
    }
}
