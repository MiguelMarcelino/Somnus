package com.somnus.server.backend.users.dto;

import java.io.Serializable;

public class AuthDto implements Serializable {
    private String token;
    private UserDto userDto;

    public AuthDto(String token, UserDto userDto) {
        this.token = token;
        this.userDto = userDto;
    }

    public String getToken() {
        return token;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
