package com.somnus.server.backend.users.dto;

import com.somnus.server.backend.users.domain.Role;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    private UserDto() {}

    public UserDto(Integer id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public UserDto(Integer id, String username, String email, String firstName, String lastName, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
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

    public String getRole() {
        return role;
    }
}
