package com.somnus.server.backend.articles.domain;

import com.somnus.server.backend.config.DateHandler;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "article_name")
    private String articleName;

    @Column(name = "author_user_name")
    private String authorUserName;

    @Column(name = "description")
    private String description;

    @Column(name = "date_published")
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
