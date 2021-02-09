package com.somnus.server.backend.articles.domain;

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
