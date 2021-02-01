package com.somnus.server.backend.exceptions;

public enum ErrorMessage {
    NO_ARTICLE_FOUND("No article found"),
    NO_TEAMMEMBER_FOUND("No team member found."),

    ACCESS_DENIED("You do not have permission to access the requested resource"),
    BAD_REQUEST("Malformed Request"),
    INTERNAL_SERVER_ERROR("Server error"),
    DATABASE_CONNECTION_ERROR("Error connecting to database"),
    URI_NOT_PRESENT("Error with one of the URIs for database connection");

    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
