package com.somnus.server.backend.users.domain;

import org.bson.codecs.pojo.annotations.BsonProperty;

import javax.persistence.Entity;

@Entity
public class User {
    private Integer id;

    @BsonProperty(value = "user_id")
    private String userId;

    @BsonProperty(value = "display_name")
    private String displayName;

    private String photoURL;
    private String email;
    private Boolean emailVerified;
    // TODO

}
