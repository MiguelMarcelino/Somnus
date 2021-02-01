package com.somnus.server.backend.feedback.domain;

public enum Status {
    UNRESOLVED("This has not been resolved yet"),
    RESOLVED("Already resolved"),
    IN_REVIEW("The current feedback is being reviewed by a team member");

    public final String message;

    Status(String message) {
        this.message = message;
    }
}
