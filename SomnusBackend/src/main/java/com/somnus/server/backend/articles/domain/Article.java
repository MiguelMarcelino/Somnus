package com.somnus.server.backend.articles.domain;

import com.somnus.server.backend.config.DateHandler;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class Article {

    private ObjectId id;

    @BsonProperty(value = "article_name")
    private String articleName;

    @BsonProperty(value = "author_user_name")
    private String authorUserName;

    @BsonProperty(value = "description")
    private String description;

    @BsonProperty(value = "date_published")
    private LocalDateTime datePublished;

    @BsonProperty(value = "topic")
    private ArticleTopic topic;

    @BsonProperty(value = "content")
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
