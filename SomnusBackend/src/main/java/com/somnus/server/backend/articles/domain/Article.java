package com.somnus.server.backend.articles.domain;

import com.somnus.server.backend.post.domain.Post;
import com.somnus.server.backend.topic.ArticleTopic;
import com.somnus.server.backend.users.domain.User;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "articles")
@Proxy(lazy = false)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
    public Article(User author, String articleName, String authorUserName, String description,
                   ArticleTopic topic, String content) {
        super(author, articleName, authorUserName, description, content);
        this.topic = topic;
    }

    public ArticleTopic getTopic() {
        return topic;
    }

    public void setTopic(ArticleTopic topic) {
        this.topic = topic;
    }
}
