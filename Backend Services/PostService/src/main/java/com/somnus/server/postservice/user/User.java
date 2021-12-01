package com.somnus.server.postservice.user;

public class User {

    private Integer id;
    private String firebaseGeneratedUserID;
    private String firstName;
    private String lastName;
    private String displayName;
    private String email;
    private Role role;

    public Integer getId() {
        return id;
    }

    public String getFirebaseGeneratedUserID() {
        return firebaseGeneratedUserID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
