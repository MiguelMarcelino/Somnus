package com.somnus.server.backend.articles.dto;

import com.somnus.server.backend.articles.domain.ArticleTopic;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ArticleDto implements Serializable {
    private String articleName;
    private String authorUserName;
    private String description;
    private LocalDateTime datePublished;
    private String topic;
    private String content;

    public ArticleDto() {}

    public ArticleDto(String articleName, String authorUserName, String description,
                      LocalDateTime datePublished, String topic, String content) {
        this.articleName = articleName;
        this.authorUserName = authorUserName;
        this.description = description;
        this.datePublished = datePublished;
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

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }
}
