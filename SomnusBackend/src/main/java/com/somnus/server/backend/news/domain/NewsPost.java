package com.somnus.server.backend.news.domain;

import com.somnus.server.backend.post.domain.Post;
import com.somnus.server.backend.users.domain.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "news_post")
public class NewsPost extends Post {

    public NewsPost() {
        super();
    }

    /**
     * Create a NewsPost for storing in the Database
     *
     * @param author
     * @param newsPostName
     * @param authorUserName
     * @param description
     * @param content
     */
    public NewsPost(User author, String newsPostName, String authorUserName, String description, String content) {
        super(author, newsPostName, authorUserName, description, content);
    }


}
