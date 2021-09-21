package com.somnus.server.backend.news.dto;

import com.somnus.server.backend.post.dto.PostDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NewsPostDTO extends PostDTO implements Serializable {

    public NewsPostDTO() {
        super();
    }

    public NewsPostDTO(String id, String newsPostName, String normalizedName, String authorUserName, String userId, String description,
                       LocalDateTime datePublished, LocalDateTime lastUpdate, boolean isDeleted, String content) {
        super(id, newsPostName, normalizedName, authorUserName, userId, description, datePublished, lastUpdate, isDeleted,
                content);
    }

}
