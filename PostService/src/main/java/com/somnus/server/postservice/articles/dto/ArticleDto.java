package com.somnus.server.postservice.articles.dto;

import com.somnus.server.postservice.post.dto.PostDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ArticleDto extends PostDTO implements Serializable {

    private String topic;
    private String normalizedTopic;

    public ArticleDto() {
    }

    public ArticleDto(String id, String articleName, String normalizedName, String authorUserName, String firebaseGeneratedUserID, String description,
                      LocalDateTime datePublished, LocalDateTime lastUpdate, String topic, String normalizedTopic, boolean isDeleted, String content) {
        super(id, articleName, normalizedName, authorUserName, firebaseGeneratedUserID, description, datePublished, lastUpdate, isDeleted,
                content);
        this.topic = topic;
        this.normalizedTopic = normalizedTopic;
    }

    public String getTopic() {
        return topic;
    }

    public String getNormalizedTopic() {
        return normalizedTopic;
    }

}
