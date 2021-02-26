package com.somnus.server.backend.articles.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleDto implements Serializable {
    private String id;
    private String articleName;
    private String authorUserName;
    private String userId;
    private String description;
    private String datePublished;
    private String lastUpdate;
    private String topic;
    private String content;

    public ArticleDto() {}

    public ArticleDto(String id, String articleName, String authorUserName, String userId, String description,
                      LocalDateTime datePublished, LocalDateTime lastUpdate, String topic, String content) {
        this.id = id;
        this.articleName = articleName;
        this.authorUserName = authorUserName;
        this.userId = userId;
        this.description = description;
        this.datePublished = datePublished.format(DateTimeFormatter.ISO_DATE);
        if(lastUpdate != null) {
            this.lastUpdate = lastUpdate.format(DateTimeFormatter.ISO_DATE);
        }
        this.topic = topic;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }
}
