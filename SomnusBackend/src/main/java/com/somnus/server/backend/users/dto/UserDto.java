package com.somnus.server.backend.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.somnus.server.backend.users.domain.Role;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    private int id;
    private String username;
    @JsonProperty(value = "firstName")
    private String firstName;
    @JsonProperty(value = "lastName")
    private String lastName;
    private String displayName;
    private String email;
    private String role;

    private UserDto() {}

    public UserDto(Integer id, String username, String email, String displayName, String firstName,
                   String lastName, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.displayName = displayName;
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

    public String getDisplayName() {
        return displayName;
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
