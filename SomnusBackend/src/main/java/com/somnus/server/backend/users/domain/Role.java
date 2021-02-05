package com.somnus.server.backend.users.domain;

public enum Role {
    ADMIN("Admin"),
    EDITOR("Editor"),
    USER("Reader");

    public String name;

    Role(String name) {this.name = name;}
}
