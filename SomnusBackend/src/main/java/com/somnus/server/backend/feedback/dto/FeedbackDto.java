package com.somnus.server.backend.feedback.dto;

import java.io.Serializable;


public class FeedbackDto implements Serializable {

    // Will be retrieved by principal
    // public User user;

    private String title;
    private String content;

    public FeedbackDto(){}

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
