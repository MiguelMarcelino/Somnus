package com.somnus.server.backend.exceptions;

public enum ErrorMessage {
    NO_ARTICLE_FOUND("No article found"),
    NO_TEAMMEMBER_FOUND("No team member found."),

    ACCESS_DENIED("You do not have permission to access the requested resource"),
    BAD_REQUEST("Malformed Request"),
    INTERNAL_SERVER_ERROR("Server error"),

    FIREBASE_USER_DOES_NOT_EXIST("Firebase user does not exist"),
    INVALID_AUTH_TOKEN("Invalid auth token");

    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
