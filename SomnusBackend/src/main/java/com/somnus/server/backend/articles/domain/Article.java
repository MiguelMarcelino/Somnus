package com.somnus.server.backend.articles.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.somnus.server.backend.config.DateHandler;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Proxy(lazy=false)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "article_name")
    private String articleName;

    @Column(name = "author_user_name")
    private String authorUserName;

    @Column(name = "description")
    private String description;

    @Column(name = "date_published")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="dd/MM/yyyy hh:mm")
    private LocalDateTime datePublished;

    @Enumerated(EnumType.STRING)
    private ArticleTopic topic;

    @Column(name = "content")
    private String content;

    public Article () {}

    /**
     * Create a new Article for storing in Database
     * @param articleName
     * @param authorUserName
     * @param description
     * @param topic
     * @param content
     */
    public Article(String articleName, String authorUserName, String description,
                   ArticleTopic topic, String content) {
        this.articleName = articleName;
        this.authorUserName = authorUserName;
        this.description = description;
        this.datePublished = DateHandler.now();
        this.topic = topic;
        this.content = content;
    }

    public Integer getId() {
        return this.id;
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
