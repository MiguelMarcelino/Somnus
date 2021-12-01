package com.somnus.server.bakend.authservice.users.domain;

public enum Role {
    ADMIN("Admin"),
    EDITOR("Editor"),
    USER("User"),
    MANAGER("Manager");

    public String name;

    Role(String name) {this.name = name;}
}
