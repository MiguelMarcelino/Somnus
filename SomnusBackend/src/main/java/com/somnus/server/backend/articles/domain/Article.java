package com.somnus.server.backend.articles.domain;

import com.somnus.server.backend.config.DateHandler;

import java.time.LocalDateTime;

public class Article {
    private String articleName;
    private String authorUserName;
    private String description;
    private LocalDateTime datePublished;
    private ArticleTopic topic;
    private String content;

    public Article () {}

    /**
     * Create a new Article for storing in Database
     * @param articleName
     * @param authorUserName
     * @param description
     * @param datePublished
     * @param topic
     * @param content
     */
    public Article(String articleName, String authorUserName, String description,
                   LocalDateTime datePublished, ArticleTopic topic, String content) {
        this.articleName = articleName;
        this.authorUserName = authorUserName;
        this.description = description;
        this.datePublished = DateHandler.now();
        this.topic = topic;
        this.content = content;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDatePublished() {
        return datePublished;
    }

    public ArticleTopic getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }
}
