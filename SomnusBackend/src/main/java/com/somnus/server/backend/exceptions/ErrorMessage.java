package com.somnus.server.backend.exceptions;

public enum ErrorMessage {
    ACCESS_DENIED("You do not have permission to access the requested resource"),
    BAD_REQUEST("Malformed Request"),
    INTERNAL_SERVER_ERROR("Server error");

    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
