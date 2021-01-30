package com.somnus.server.backend.exceptions;

public enum ErrorMessage {
    NO_ARTICLE_FOUND("No article found"),
    NO_ARTICLES_TO_DELETE("There are no articles that can be deleted"),
    ARTICLE_INSERT_FAILED("Failed on inserting a new article"),

    ACCESS_DENIED("You do not have permission to access the requested resource"),
    BAD_REQUEST("Malformed Request"),
    INTERNAL_SERVER_ERROR("Server error"),
    DATABASE_CONNECTION_ERROR("Error connecting to database");

    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
