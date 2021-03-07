package com.somnus.server.backend.articles.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.somnus.server.backend.config.DateHandler;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.users.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Proxy;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "articles")
@Proxy(lazy = false)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "article_name")
    private String articleName;

    @Column(name = "author_user_name")
    private String authorUserName;

    @Column(name = "description")
    private String description;

    @Column(name = "last_update")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime lastUpdate = null;

    @Column(name = "date_published")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime datePublished;

    @Enumerated(EnumType.STRING)
    private ArticleTopic topic;

    @Lob
    private byte[] content;

    public Article() {
    }

    /**
     * Create a new Article for storing in Database
     *
     * @param articleName
     * @param authorUserName
     * @param description
     * @param topic
     * @param content
     */
    public Article(User author, String articleName, String authorUserName, String description,
                   ArticleTopic topic, String content) {
        this.author = author;
        this.articleName = articleName;
        this.authorUserName = authorUserName;
        this.description = description;
        this.datePublished = DateHandler.now();
        this.lastUpdate = DateHandler.now();
        this.topic = topic;
        writeData(content);
    }

    public Integer getId() {
        return this.id;
    }

    public User getAuthor() {
        return author;
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

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public ArticleTopic getTopic() {
        return topic;
    }

    public String getContent() {
        return new String(content, StandardCharsets.UTF_8);
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void updateLastUpdate() {
        this.lastUpdate = DateHandler.now();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTopic(ArticleTopic topic) {
        this.topic = topic;
    }

    public void setContent(String content) {
        writeData(content);
    }

    private void writeData(String content) {
        byte[] contentData = content.getBytes(StandardCharsets.UTF_8);
        this.content = contentData;
    }
}
