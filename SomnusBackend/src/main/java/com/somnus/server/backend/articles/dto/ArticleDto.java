package com.somnus.server.backend.articles.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleDto implements Serializable {
    private String id;
    private String articleName;
    private String normalizedName;
    private String authorUserName;
    private String userId;
    private String description;
    private String datePublished;
    private String lastUpdate;
    private String topic;
    private String normalizedTopic;
    private String content;

    public ArticleDto() {}

    public ArticleDto(String id, String articleName, String normalizedName, String authorUserName, String userId, String description,
                      LocalDateTime datePublished, LocalDateTime lastUpdate, String topic, String normalizedTopic, String content) {
        this.id = id;
        this.articleName = articleName;
        this.normalizedName = normalizedName;
        this.authorUserName = authorUserName;
        this.userId = userId;
        this.description = description;
        this.datePublished = datePublished.format(DateTimeFormatter.ISO_DATE);
        if(lastUpdate != null) {
            this.lastUpdate = lastUpdate.format(DateTimeFormatter.ISO_DATE);
        }
        this.topic = topic;
        this.normalizedTopic = normalizedTopic;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getNormalizedName() {
        return normalizedName;
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

    public String getNormalizedTopic() {
        return normalizedTopic;
    }

    public String getContent() {
        return content;
    }
}
