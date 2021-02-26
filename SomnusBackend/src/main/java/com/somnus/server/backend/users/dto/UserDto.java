package com.somnus.server.backend.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.somnus.server.backend.users.domain.Role;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    private int id;
    @JsonProperty(value = "userId")
    private String userId;
    @JsonProperty(value = "firstName")
    private String firstName;
    @JsonProperty(value = "lastName")
    private String lastName;
    private String displayName;
    private String email;
    private String role;

    public UserDto() {}

    public UserDto(Integer id, String userId, String email, String displayName, String firstName,
                   String lastName, String role) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getUserID() {
        return userId;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
