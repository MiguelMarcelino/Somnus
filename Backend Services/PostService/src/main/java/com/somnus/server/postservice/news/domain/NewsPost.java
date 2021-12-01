package com.somnus.server.postservice.news.domain;

import com.somnus.server.postservice.post.domain.Post;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "news_post")
@Proxy(lazy = false)
public class NewsPost extends Post {

    public NewsPost() {
        super();
    }

    /**
     * Create a NewsPost for storing in the Database
     *
     * @param userID
     * @param newsPostName
     * @param authorUserName
     * @param description
     * @param content
     */
    public NewsPost(Integer userID, String newsPostName, String authorUserName, String firebaseGeneratedUserID, String description, String content) {
        super(userID, newsPostName, authorUserName, firebaseGeneratedUserID, description, content);
    }


}
