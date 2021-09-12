package com.somnus.server.postservice.articles.domain;

import com.somnus.server.postservice.post.domain.Post;
import com.somnus.server.postservice.topic.ArticleTopic;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "articles")
@Proxy(lazy = false)
public class Article extends Post {

    @Enumerated(EnumType.STRING)
    private ArticleTopic topic;

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
    public Article(Integer userID, String articleName, String authorUserName, String description,
                   String firebaseGeneratedUserID, ArticleTopic topic, String content) {
        super(userID, articleName, authorUserName, firebaseGeneratedUserID, description, content);
        this.topic = topic;
    }

    public ArticleTopic getTopic() {
        return topic;
    }

    public void setTopic(ArticleTopic topic) {
        this.topic = topic;
    }
}
