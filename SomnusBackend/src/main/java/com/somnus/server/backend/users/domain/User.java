package com.somnus.server.backend.users.domain;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class User {
    private ObjectId id;

    @BsonProperty(value = "user_id")
    private String userId;

    @BsonProperty(value = "display_name")
    private String displayName;

    private String photoURL;
    private String email;
    private Boolean emailVerified;
    // TODO

}
