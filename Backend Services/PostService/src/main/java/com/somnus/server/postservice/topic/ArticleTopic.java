package com.somnus.server.postservice.topic;

// TODO: Needs to be stored in database for users to add more topics
public enum ArticleTopic {
    COMPUTER_SCIENCE("Computer Science"),
    MATHEMATICS("Mathematics"),
    PHYSICS("Physics"),
    ENGINEERING("Engineering"),
    CHEMISTRY("Chemistry"),
    GAMING("Gaming");

    public final String name;

    ArticleTopic(String name) {
        this.name = name;
    }
}
