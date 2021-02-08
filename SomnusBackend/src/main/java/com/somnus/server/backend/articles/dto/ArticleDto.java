package com.somnus.server.backend.articles.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.articles.domain.ArticleTopic;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ArticleDto implements Serializable {
    private Integer id;
    private String articleName;
    private String authorUserName;
    private String description;
    private LocalDateTime datePublished;
    private String topic;
    private String content;

    public ArticleDto() {}

    public ArticleDto(Integer id, String articleName, String authorUserName, String description,
                      LocalDateTime datePublished, String topic, String content) {
        this.id = id;
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
