package com.somnus.server.postservice.news.dto;

import com.somnus.server.postservice.post.dto.PostDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NewsPostDTO extends PostDTO implements Serializable {

    public NewsPostDTO() {
        super();
    }

    public NewsPostDTO(String id, String newsPostName, String normalizedName, String authorUserName, String firebaseGeneratedUsername, String description,
                       LocalDateTime datePublished, LocalDateTime lastUpdate, String content) {
        super(id, newsPostName, normalizedName, authorUserName, firebaseGeneratedUsername, description, datePublished, lastUpdate,
                content);
    }

}
